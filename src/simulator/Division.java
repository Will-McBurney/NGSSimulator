/**
 * Division Interface
 * 
 * I admit this interface is rather half-cocked at the moment. My thought process here 
 * is that I could extend this architecture to other leagues and hopefully be able to reuse
 * some code. But at the moment, I have no such plans to do that.
 * 
 * This Interface currently isn't used, though the concrete class NGSDivision is.
 */

package simulator;

import java.util.List;

public interface Division {
	/**
	 * Simple getter for the Division name. Example in NGS, "H", "A", "BE", etc.
	 * @return
	 */
	public String getName();
	
	/**
	 * Simulate any unresolved games, updating the team objects.
	 */
	public void simulate();
	
	/**
	 * Get a list of the team objects in the division.
	 * 
	 * Possible change: refactor this function out, since it's un-used at the moment
	 * @return List of Team Objects in the division.
	 */
	public List<Team> getTeams();
}
