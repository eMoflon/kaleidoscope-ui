package com.kaleidoscope.ui.delta.javabased.operational;

public abstract class Operation{
	
	public abstract Deltameta.Operation toOperationalEMF();
	public abstract void executeOperation();
}
