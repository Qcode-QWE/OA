package cn.QEcode.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.QEcode.domain.Article;
import cn.QEcode.domain.Page;
import cn.QEcode.domain.Reply;
import cn.QEcode.domain.Topic;
import cn.QEcode.domain.User;
import cn.QEcode.service.ForumService;
import cn.QEcode.service.ReplyService;
import cn.QEcode.service.TopicService;

/**  
* <p>Title: SolrSearch.java</p>  
* <p>Description:使用solr来搜索帖子 </p>   
* @author QEcode  
* @date 2018年10月31日  
* @version 1.0  
*/

@Service
public class SolrUtils {
    
    private final static String url = "http://localhost:8983/solr/OA";
    private static SolrClient solrClient = new HttpSolrClient.Builder(url).build();
    
    @Autowired
    private TopicService topicService;
    @Autowired
    private ReplyService replyService;
    @Autowired
    private ForumService forumService;
    
    /**
     * @throws IOException 
     * @throws SolrServerException 
     * @Description:将论坛帖子信息传到solr中
     */
    public  void InitSolr() throws SolrServerException, IOException{
	//查询所有的主题
	List<Topic> topics = topicService.findAll();
	//将所有主题传到solr中
	for (Topic topic : topics) {
	    //创建文档
	    SolrInputDocument doc = new SolrInputDocument();
	    //向文档中添加对象
	    doc.addField("id","topic"+topic.getTopicId());
	    doc.addField("Article_content",topic.getContent());
	    doc.addField("Article_title",topic.getTitle());
	    doc.addField("Article_autho",topic.getAuthor().getName());
	    doc.addField("forum_id",topic.getForum().getForumId());
	    //表示是主题
	    doc.addField("Article_type","0");
	    solrClient.add(doc);
	}
	
	//查询所有回帖
	List<Reply> replies = replyService.findAll();
	//将所有的回帖传到solr中
	for (Reply reply : replies) {
	    //创建文档
	    SolrInputDocument doc = new SolrInputDocument();
	    //向文档中添加对象
	    doc.addField("id","reply"+reply.getReplyId());
	    doc.addField("Article_content",reply.getContent());
	    doc.addField("Article_title",reply.getTitle());
	    doc.addField("Article_autho",reply.getAuthor().getName());
	    doc.addField("topic_id",reply.getTopic().getTopicId());
	    //表示是回帖
	    doc.addField("Article_type","1");
	    solrClient.add(doc);
	}
	solrClient.commit();
    }
    
    /**
     * @Description:根据关键字查询solr,并返回查询得到主题和回帖
     * @param condition
     * @return
     * @throws IOException 
     * @throws SolrServerException 
     */
    public Page SolrSearch(String[] condition,int pageNum,int type) throws SolrServerException, IOException{
	
	//添加条件
	SolrQuery query = new SolrQuery();
	query.setQuery(condition[0]);
	query.setFilterQueries("Article_type:"+type);
	if(type==0){
	    query.setFilterQueries("forum_id:"+condition[1]);
	}else {
	    query.setFilterQueries("topic_id:"+condition[1]);	
	}
	//添加回显内容
	query.addField("id");
	query.addField("Article_content");
	query.addField("Article_title");
	query.addField("Article_autho");
	query.addField("forum_id");
	query.addField("topic_id");
	//设置分页
	query.setStart((pageNum-1)*5);
	query.setRows(5);
	//设置默认搜索域
	query.set("df", "Article_keywords");
	//高亮显示
	query.setHighlight(true);
	//添加高亮显示的域
	query.addHighlightField("Article_title,Article_content,Article_autho");
	query.setHighlightSimplePre("<em style='color: red;'>"); // 设置高亮显示的前缀
	query.setHighlightSimplePost("</em>"); // 设置高亮显示的后缀
	 
	QueryResponse response = solrClient.query(query);
	//获取doc文档
	SolrDocumentList solrDocumentList = response.getResults();
	//查询结果总记录数
	Long sum = solrDocumentList.getNumFound();
	//构建page
	Page page = new Page(pageNum, sum.intValue());
	List result = new ArrayList();
	for (SolrDocument solrDocument : solrDocumentList) {
	    //id
	    String id = (String) solrDocument.get("id");
	    String forum_id = (String) solrDocument.get("forum_id");
	    String topic_id = (String) solrDocument.get("topic_id");
	    String title = null;
	    String autho = null;
	    String content = null;
	    
	    // 取高亮显示
	    Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
	    List<String> titles = highlighting.get(solrDocument.get("id")).get("Article_title");
	    List<String> authos = highlighting.get(solrDocument.get("id")).get("Article_autho"); 
	    List<String> contents = highlighting.get(solrDocument.get("id")).get("Article_content");
	    //将高亮结果取出来
	    if(titles!=null&&titles.size()>0){
		title = titles.get(0);
	    }else{
		title = (String) solrDocument.get("Article_title");
	    }
	    if(authos!=null&&authos.size()>0){
		autho = authos.get(0);
	    }else{
		autho = (String) solrDocument.get("Article_autho");
	    }
	    if(contents!=null&&contents.size()>0){
		content = contents.get(0);
	    }else{
		content = (String) solrDocument.get("Article_content");
	    }
	    //主题
	    if(type==0){
		Topic topic = new Topic();
		id = id.replace("topic","");
		topic.setTopicId(Long.valueOf(id));
		Integer forumId = Integer.valueOf(forum_id);
		topic.setForum(forumService.findById(forumId.longValue()));
		topic.setContent(content);
		topic.setTitle(title);
		User user = new User();
		user.setName(autho);
		topic.setAuthor(user);
		result.add(topic);
	    }else{
		//回帖
		Reply reply = new Reply();
		id = id.replace("reply","");
		reply.setReplyId(Long.valueOf(id));
		Integer topicId = Integer.valueOf(topic_id);
		reply.setTopic(topicService.findById(topicId.longValue()));
		reply.setContent(content);
		reply.setTitle(title);
		User user = new User();
		user.setName(autho);
		reply.setAuthor(user);
		result.add(reply);	
	    }
	 }
	page.setRecords(result);
	return page;
    }
    
    public static void main(String[] args) throws SolrServerException, IOException {
	SolrUtils solrUtils = new SolrUtils();
	solrUtils.InitSolr();
    }
}
