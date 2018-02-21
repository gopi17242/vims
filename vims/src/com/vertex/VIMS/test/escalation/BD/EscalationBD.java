/*
 * @author: santhosh.k
 * @creation date: 
 * @file name : EscalationBD.java
 * @description: 
 * 			It is the Business delegate class  of the Escalation page.It is called when the user clicks on the Escalation link under Issues.
*/
package com.vertex.VIMS.test.escalation.BD;

import java.util.ArrayList;
import com.vertex.VIMS.test.escalation.DAO.*;
public class EscalationBD
{
	/*
	 * Used to retrieve the escalated issues from the DAO to action controller.s
	*/
	public static ArrayList getEscalatedIssuesListBD(String strUserID)
	{
		ArrayList EscalatedIssuesListBD=EscalationDAO.getEscalatedIssuesListDAO(strUserID);
		return EscalatedIssuesListBD;
	}
}