package com.wow.entities;

import com.wow.utils.types.MovementSpeed;

public abstract class Unit extends WorldObject{
	protected MovementSpeed movement;
	
	public MovementSpeed getMovement(){
		return movement;
	}
}
