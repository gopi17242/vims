package com.vertex.VIMS.test.common;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
 
public class PaginationAction extends Action
{
	public ActionForward execute(ActionMapping actionMapping,ActionForm actionForm, HttpServletRequest request,HttpServletResponse response)
	{
		HttpSession session=request.getSession();
		String forwardPage=request.getParameter("forwardPage");
		String source=request.getParameter("source");
		ArrayList arrayList=(ArrayList)session.getAttribute(source);
		int listSize=arrayList.size();
		ArrayList setArrayList=new ArrayList();
		int rowsPerPage=new Integer(request.getParameter("rowsPerPage")).intValue();
		int endIndex=((new Integer(request.getParameter("pageNo")).intValue())*rowsPerPage)-1;
		int currPage=(endIndex+1)/rowsPerPage;
		if(endIndex>listSize-1)
		{
			endIndex=listSize-1;
		}
		int startIndex=endIndex-rowsPerPage+1;
		for(int i=startIndex;i<=endIndex;i++)
		{
			setArrayList.add(arrayList.get(i));			
		}
		System.out.println("==============source============"+source);
		request.setAttribute("currPage", new Integer(currPage));
		request.setAttribute(source, setArrayList);
		
		return actionMapping.findForward(forwardPage);
	}
}
