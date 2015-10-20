/**
 * Title: SYSC 3110 Project
 * @author Justin Krol
 * Student Number: 100941980
 * Team: nullSquad
 */

package nullSquad.network;

import nullSquad.document.*;
import java.util.ArrayList;
import java.util.List;

public class Network
{
	private int nextAvailableUserID = 1;
	private int nextAvailableDocID = 1;
	private List<User> users;
	private List<Document> allDocuments;
	
	/**
	 * @author Justin Krol
	 * Creates a Network with a list of users and a list of documents
	 * 
	 * @param name	Name of the document
	 * @param tag	Search tag that will be used to find this document
	 * @param producer	Creator of the document
	 * 
	 */
	public Network()
	{
		users = new ArrayList<>();
		allDocuments = new ArrayList<>();
	}
	
	/**
	 * @author Justin Krol
	 * Add a user to the network if it has not already been added
	 * @param user who will be added to the network
	 * @return userID if the user could be added, -1 if the user could not
	 * because it was already registered or the user argument was null
	 */
	public int registerUser(User user)
	{
		if(user != null)
		{
			if(users.contains(user)){
				//System.out.println("User has already been added to the network");
				return -1;
			}
			users.add(user);
			nextAvailableUserID++;
			return nextAvailableUserID-1;
		}
		//System.out.println("No user object to register (null)");
		return -1;
	}
	
	/**
	 * @author Justin Krol
	 * Remove a user from the network if it is registered
	 * @param user who will be removed from the network
	 * @return true if the user could be removed, false if the user could not
	 * because it was not registered or the user argument was null
	 */
	public boolean deactivateUser(User user)
	{
		if(user != null)
		{
			if(!users.contains(user)){
				//System.out.println("User is not currently registered on the network");
				return false;
			}
			users.remove(user);
			return true;
		}
		//System.out.println("No user object to remove (null)");
		return false;
	}
	
	//being moved from Network.java to Consumer.java
	public int calculatePayoff(List<Document> documents)
	{
		return 0;
	}
	
	/**
	 * Searches the network for the top k documents
	 * @param user The user that will search the network
	 * @param topK Number of results to be returned
	 * @return The top K results from the network
	 * @author MVezina
	 */
	public List<Document> search(User user, int topK)
	{
		if(allDocuments.size() < topK)
		{
			return allDocuments;
		}
		
		List<Document> topKDocuments = new ArrayList<Document>();		
		
		for(Document doc: allDocuments)
		{
			// If document isnt liked by the searching user
			if(!doc.getUserLikes().contains(user))
			{
				// If tag matches users taste
				if(doc.getTag().equals(user.getTaste()))
				{				
					topKDocuments.add(doc);
					continue;
				}	
				
				
				for (User u : user.getFollowing())
				{
					if(doc.getUserLikes().contains(u))
					{
						topKDocuments.add(doc);
						break;
					}
				}
			}			
		}
		
		if(topKDocuments.size() < topK)
		{
			for(Document doc : allDocuments)
			{
				if(!topKDocuments.contains(doc))
				{
					if(topKDocuments.size() == topK)
						break;
					
					topKDocuments.add(doc);
				}
			}
			
			
		}
		
		
		
		
		//After all documents have been checked, return the list of topK documents
		return topKDocuments;
	}
	
	/**
	 * @author Justin Krol
	 * Add a document to the network if it has not already been added
	 * @param document that will be added to the network
	 * @return true if the document could be added, false if the document could not
	 * because it was already added or the document argument was null
	 */
	public boolean addDocument(Document doc)
	{
		if(doc != null)
		{
			if(allDocuments.contains(doc)){
				System.out.println("Document has already been added to the network");
				return false;
			}
			doc.setDocumentID(nextAvailableDocID ++); 
			allDocuments.add(doc);
			return true;
		}
		//System.out.println("No document object to add (null)");
		return false;
	}
	
	/**
	 * @author Justin Krol
	 * Remove a document from the network if it has been added
	 * @param document that will be removed from the network
	 * @return true if the document could be removed, false if the document could not
	 * because it was not on the network or the document argument was null
	 */
	public boolean removeDocument(Document doc)
	{
		if(doc != null)
		{
			if(!allDocuments.contains(doc)){
				//System.out.println("User is not currently registered on the network");
				return false;
			}
			allDocuments.remove(doc);
			return true;
		}
		//System.out.println("No user object to remove (null)");
		return false;
	}
	
	/**
	 * @author Justin Krol
	 * Getter for the documents list
	 * @return list of documents
	 */
	public List<Document> getAllDocuments()
	{
		return allDocuments;
	}
	
	/**
	 * @author Justin Krol
	 * Getter for the list of users
	 * @return list of users
	 */
	public List<User> getUsers()
	{
		return users;
	}
	
	
	//Note: This method will likely become obsolete once documents are sorted
	//using a more sophisticated approach.  Documents will likely implement comparable
	//and will be sorted by the search function.
	/**
	 * @author Justin Krol
	 * Helper method for search used to find the minimum liked document in a list of documents
	 * @param user who will be removed from the network
	 * @return true if the user could be removed, false if the user could not
	 * because it was not registered or the user argument was null
	 */
	private Document findDocMinLikes(List<Document> topKDocuments)
	{
		Document dMinLikes = topKDocuments.get(0);
		
		for(Document d: topKDocuments)
		{
			if(d.getUserLikes().size() < dMinLikes.getUserLikes().size())
			{
				dMinLikes = d;
			}
		}
		return dMinLikes;
	}

}
