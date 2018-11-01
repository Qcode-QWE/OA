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

import cn.QEcode.domain.Reply;
import cn.QEcode.domain.Topic;
import cn.QEcode.service.ReplyService;
import cn.QEcode.service.TopicService;

/**  
* <p>Title: SolrSearch.java</p>  
* <p>Description:使用solr来搜索帖子 </p>   
* @author QEcode  
* @date 2018年10月31日  
* @version 1.0  
*/ 
public class SolrUtils {
    
    private final static String url = "http://localhost:8983/solr/OA";
    private static SolrClient solrClient = new HttpSolrClient.Builder(url).build();
    
    @Resource(name="TopicService")
    private TopicService topicService;
    @Resource(name="ReplyService")
    private ReplyService replyService;
    
    
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
	    doc.addField("id",UUID.randomUUID().toString());
	    doc.addField("Article_id",topic.getTopicId());
	    doc.addField("Article_content",topic.getContent());
	    doc.addField("Article_title",topic.getTitle());
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
	    doc.addField("id",UUID.randomUUID().toString());
	    doc.addField("Article_id",reply.getReplyId());
	    doc.addField("Article_content",reply.getContent());
	    doc.addField("Article_title",reply.getTitle());
	    //表示是回帖
	    doc.addField("Article_type","1");
	    solrClient.add(doc);
	}
    }
    
    /**
     * @Description:根据关键字查询solr,并返回查询得到主题和回帖
     * @param condition
     * @return
     * @throws IOException 
     * @throws SolrServerException 
     */
    public Map<String, List<Object>> SolrSearch(String condition) throws SolrServerException, IOException{
	
	//添加条件
	SolrQuery query = new SolrQuery("*:"+condition);
	//添加回显内容
	query.addField("Article_id");
	query.addField("Article_content");
	query.addField("Article_title");
	query.addField("Article_type");
	QueryResponse response = solrClient.query(query);
	//获取doc文档
	SolrDocumentList solrDocumentList = response.getResults();
	//将doc文档里的内容为为主题和回帖
	Map<String, List<Object>> map = new HashMap<String, List<Object>>();
	List<Object> topics = new ArrayList<Object>();
	List<Object> replies = new ArrayList<Object>();
	map.put("topics", topics);
	map.put("replies", replies);
	
	for (SolrDocument solrDocument : solrDocumentList) {
	    String type = (String) solrDocument.get("Article_type");
	    //如果是主题
	    if("0".equals(type)){
		Topic topic = new Topic();
		topic.setTopicId((Long)solrDocument.get("Article_id"));
		topic.setTitle((String)solrDocument.get("Article_title"));
		topic.setContent((String)solrDocument.get("Article_content"));
		
		map.get("topics").add(topic);
	    }else{
		//如果是回帖
		Reply reply = new Reply();
		reply.setReplyId((Long)solrDocument.get("Article_id"));
		reply.setTitle((String)solrDocument.get("Article_title"));
		reply.setContent((String)solrDocument.get("Article_content"));
		map.get("replies").add(reply);
	    }
	    
	    
	}
	return map;
    }
    
    public static void main(String[] args) throws SolrServerException, IOException {
	SolrUtils solrUtils = new SolrUtils();
	solrUtils.InitSolr();
    }
}
