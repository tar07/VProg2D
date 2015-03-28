/**
 * 
 */
package com.mygdx.game;

import java.util.ArrayList;

/**
 * @description Stores the User's name and an array of all projects belonging to
 *              the User.
 * 
 * @author Trevor Richardson
 * @date Mar. 22th 2015
 * @module CreateProject
 */
public class User {

	private String name;
	private ArrayList projects;

	/**
	 * 
	 */
	public User() {
		//
	}

	public void setName(String _name) {
		name = _name;
	}

	public String getName() {
		return name;
	}

	public void addProject(Project _project) {
		projects.add(_project);
	}

	/* returns the actual Projects and not a copy */
	public ArrayList getProjects() {
		return projects;
	}

}