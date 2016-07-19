package bitbitree;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.xuxinting.FuncCode.GetComment;
import cn.xuxinting.FuncCode.GetMessage;
import cn.xuxinting.FuncCode.IMEILog;
import cn.xuxinting.FuncCode.MessageApprove;
import cn.xuxinting.FuncCode.NewComment;
import cn.xuxinting.FuncCode.NewMessage;
import cn.xuxinting.FuncCode.NewUser;
import cn.xuxinting.FuncCode.UserChangeName;
import cn.xuxinting.FuncCode.UserChangePasword;
import cn.xuxinting.JsonCheck.GetCommentJsonCheck;
import cn.xuxinting.JsonCheck.MessageRequestJsonCheck;
import cn.xuxinting.JsonCheck.NewApproveJsonCheck;
import cn.xuxinting.JsonCheck.NewCommentJsonCheck;
import cn.xuxinting.JsonCheck.NewMessageJsonCheck;
import cn.xuxinting.JsonCheck.NewUserJsonCheck;
import cn.xuxinting.JsonCheck.UserChangePassJsonCheck;
import cn.xuxinting.JsonCheck.UserLogJsonCheck;
import cn.xuxinting.JsonCheck.UserNameChangeJsonCheck;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * Servlet implementation class Bitbitree
 */
@WebServlet("/Bitbitree")
public class Bitbitree extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Bitbitree() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		/*
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = request.getReader();
		try {
		    String line;
		    while ((line = reader.readLine()) != null) {
		        sb.append(line).append('\n');
		    }
		} finally {
		    reader.close();
		}
        */
		request.setCharacterEncoding("UTF-8");
		
        String acceptjson = request.getParameter("bitree");
        boolean respon = true;
        
        if(acceptjson.length() != 0){
			JSONObject json = (JSONObject)JSONSerializer.toJSON(acceptjson);
			String FuncKey = json.getString("FuncCode");
			
			JSONObject Return = json;
			
			if(FuncKey.equals("NewUser")){
				NewUserJsonCheck newUserJsonCheck = new NewUserJsonCheck(json);
				if(newUserJsonCheck.Check()){
					NewUser newUser = new NewUser(json);
					Return = newUser.getJSON();
				}else{
					respon = false;
				}
			}else if(FuncKey.equals("NewMessage")){
				NewMessageJsonCheck newMessageJsonCheck = new NewMessageJsonCheck(json);
				if(newMessageJsonCheck.Check()){
					NewMessage newMessage = new NewMessage(json);
					Return = newMessage.getJSON();
				}else {
					respon = false;
				}
			}else if(FuncKey.equals("GetMessage")){
				MessageRequestJsonCheck messageR = new MessageRequestJsonCheck(json);
				if(messageR.Check()){
					GetMessage getMessage = new GetMessage(json);
					Return = getMessage.getJSON();
				}else {
					respon = false;
				}
			}else if(FuncKey.equals("NewComment")){
				NewCommentJsonCheck comment = new NewCommentJsonCheck(json);
				if(comment.Check()){
					NewComment newComment = new NewComment(json);
					Return = newComment.getJSON();
				}else {
					respon = false;
				}
			}else if(FuncKey.equals("UserChangeName")){
				UserNameChangeJsonCheck nameChange = new UserNameChangeJsonCheck(json);
				if(nameChange.Check()){
					UserChangeName userChangeName = new UserChangeName(json);
					Return = userChangeName.getJSON();
				}else {
					respon = false;
				}
			}else if(FuncKey.equals("UserChangePasword")){
				UserChangePassJsonCheck PassChenge = new UserChangePassJsonCheck(json);
				if(PassChenge.Check()){
					UserChangePasword userChangePasword = new UserChangePasword(json);
					Return = userChangePasword.getJSON();
				}else {
					respon = false;
				}
			}else if(FuncKey.equals("MessageApprove")){
				NewApproveJsonCheck approveJsonCheck = new NewApproveJsonCheck(json);
				if(approveJsonCheck.Check()){
					MessageApprove messageApprove = new MessageApprove(json);
					Return = messageApprove.getJSON();
				}else {
					respon = false;
				}
			}else if(FuncKey.equals("IMEILog")){
				UserLogJsonCheck logJsonCheck = new UserLogJsonCheck(json);
				if(logJsonCheck.Check()){
					IMEILog imeiLog = new IMEILog(json);
					Return = imeiLog.getJSON();
				}else {
					respon = false;
				}
			}else if(FuncKey.equals("GetComment")){
				GetCommentJsonCheck getCommentJsonCheck = new GetCommentJsonCheck(json);
				if(getCommentJsonCheck.Check()){
					GetComment getComment = new GetComment(json);
					Return = getComment.getJSON();
				}else{
					respon = false;
				}
			}else {
				respon = false;
			}
			
			if(respon){
				try{
					response.setCharacterEncoding("UTF-8");
					PrintWriter out = response.getWriter();
					out.write(Return.toString());
					out.flush();
					out.close();
				}catch(IOException e){
					
				}
			}else{
				
			}
        }
	}
}