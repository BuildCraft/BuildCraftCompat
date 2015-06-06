package mods.immibis.core.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * When a class with this annotation is loaded,
 * the loader combines a subset of the specified classes, depending on the available interfaces.
 * 
 * For example:
 * MyTileBase extends TileEntity
 * MyTile_BC extends MyTileBase implements (some BC interface)
 * MyTile_IC extends MyTileBase implements (some IC interface)
 * MyTile extends MyTileBase (annotated with
 * 		@MultiInterfaceClass(interfaces={
 * 			@Interface(check="(some BC interface)", impl="MyTile_BC"),
 * 			@Interface(check="(some IC interface)", impl="MyTile_IC")
 * 		},
 * 		base="MyTileBase")
 * 
 * when loaded could form either of these inheritance chains if BC and IC are both installed:
 * MyTile extends MyTile_BC extends MyTile_IC extends MyTileBase extends TileEntity
 * MyTile extends MyTile_IC extends MyTile_BC extends MyTileBase extends TileEntity
 * 
 * or the following chain if only BC is installed:
 * MyTile extends MyTile_BC extends MyTileBase extends TileEntity
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface MultiInterfaceClass {
	public @interface Interface {
		/**
		 * The interface to check for the existence of.
		 */
		public String check();
		/**
		 * The implementation class to use.
		 */
		public String impl();
	}
	public Interface[] interfaces();
	public String base();
}
