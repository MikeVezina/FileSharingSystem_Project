/**
 * Title: SYSC 3110 Project
 * Created By: Michael Vezina
 * Student Number: 100934579
 * Team: nullSquad
 */

package nullSquad.network;

import java.util.ArrayList;
import java.util.List;

public abstract class User
{
	protected int				userID;
	protected List<User>		followers;
	protected List<User>		following;
	protected List<Document>	likedDocuments;
	protected String			taste;

	/**
	 * Creates a user with the specified userID and taste
	 * 
	 * @param userID
	 *            User ID of the user to be created
	 * @param taste
	 *            The taste of the user
	 */
	public User(int userID, String taste)
	{
		this.userID = userID;
		this.taste = taste;

		followers = new ArrayList<>();
		following = new ArrayList<>();

		likedDocuments = new ArrayList<>();
	}

	/**
	 * The action that is run by the user when the simulator calls it. This
	 * method needs to be implemented by subclasses
	 */
	public abstract void act();

	/**
	 * Called When the User wants to like a document
	 * 
	 * @param doc
	 *            The Document that the user is going to like
	 * @return Whether or not the document was added successfully
	 */
	public boolean likeDocument(Document doc)
	{
		// Check to see if the document already exists in the list
		if (!likedDocuments.contains(doc))
			return false;

		// Adds the document to the list of liked documents
		likedDocuments.add(doc);

		// Adds this user to the list of users who like the document and return
		// the result
		return doc.likeDocument(this);
	}

	/**
	 * Unlikes a document
	 * 
	 * @param doc
	 *            the document to be unliked
	 * @return Returns whether or not the document was unliked successfully
	 */
	public boolean unlikeDocument(Document doc)
	{
		// Checks to make sure that the document has been liked before unliking
		// the document
		if (!likedDocuments.contains(doc))
			return false;

		// Removes the document from the list of liked documents and tells the
		// document that the user no longer likes it
		return likedDocuments.remove(doc) && doc.unlikeDocument(this);
	}

	/**
	 * Adds a user to the list of followers
	 * 
	 * @param user
	 *            The user to add the list of followers
	 * @return
	 */
	public boolean addFollower(User user)
	{
		// Checks to see if the user is already following this user
		if (followers.contains(user))
			return false;

		// Add the user to the list of people who are following this user
		return followers.add(user);
	}

	/**
	 * Removes the Follower from the list of followers
	 * 
	 * @param user
	 * @return Whether or not the follower was removed successfully
	 */
	public boolean removeFollower(User user)
	{
		// Returns whether or not the user was removed
		return followers.remove(user);
	}

	/**
	 * Follows the specified user
	 * 
	 * @param user
	 *            the user to be followed
	 * @return Returns whether or not the user was followed successfully
	 */
	public boolean followUser(User user)
	{
		// Checks to see if the specified user can be followed
		if (!user.addFollower(this))
			return false;

		// Add the user to the list of users being followed by this user
		return this.following.add(user);

	}

	/**
	 * Unfollows the specified user
	 * 
	 * @param user
	 *            The user to unfollow
	 * @return Whether or not the user was unfollowed successfully
	 */
	public boolean unfollowUser(User user)
	{
		// Check to see if the user was successfully unfollowed
		if (!user.removeFollower(this))
			return false;

		// Remove user from following list
		return this.following.remove(user);
	}
	
	
	
	/**
	 * Overrides the equals method (from Object)
	 * Checks to see if two user objects are the same
	 * 
	 * @param o The object to compare to
	 * @return Returns whether or not two objects are the same
	 */
	@Override
	public boolean equals(Object o)
	{
		// If the address of the two objects is the same, they are equivilent
		if(this == o)
			return true;
		
		// If the object is not an instance of user, return false
		if(! (o instanceof User))
			return false;
		
		// If the user ID of both instances are the same, they are equivilent users
		return (this.userID == ((User) o).userID);
	}

	/**
	 * Gets the string representation of the user
	 * @return Returns a string representation of this user
	 */
	@Override
	public String toString()
	{
		return "User ID: " + this.userID + "\nTaste: " + this.taste + "\nFollowers: " 
					+ followers.size() + "\nFollowing: " + this.following.size() + "\nNumber of Documents Liked: " 
					+ this.likedDocuments.size() + "\n";
	}

}