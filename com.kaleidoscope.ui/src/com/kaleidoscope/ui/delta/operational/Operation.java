package com.kaleidoscope.ui.delta.operational;

public abstract class Operation{
	
	public abstract Deltameta.Operation toOperationalEMF();
	public abstract void executeOperation();
}
