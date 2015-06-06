package mods.immibis.core.api.util;

// Ordinals are dye item metadata, not wool block metadata
// Subtract from 15 for wool block metadata

public enum Colour {
	BLACK		("black",		0.1F, 0.1F, 0.1F, "dyeBlack"),
	RED			("red",			0.8F, 0.3F, 0.3F, "dyeRed"),
	GREEN		("green",		0.4F, 0.5F, 0.2F, "dyeGreen"),
	BROWN		("brown",		0.5F, 0.4F, 0.3F, "dyeBrown"),
	BLUE		("blue",		0.2F, 0.4F, 0.8F, "dyeBlue"),
	PURPLE		("purple",		0.7F, 0.4F, 0.9F, "dyePurple"),
	CYAN		("cyan",		0.3F, 0.6F, 0.7F, "dyeCyan"),
	LIGHT_GREY	("light grey",	0.6F, 0.6F, 0.6F, "dyeLightGray"),
	GREY		("grey",		0.3F, 0.3F, 0.3F, "dyeGray"),
	PINK		("pink",		0.95F, 0.7F, 0.8F, "dyePink"),
	LIME		("lime",		0.5F, 0.8F, 0.1F, "dyeLime"),
	YELLOW		("yellow",		0.9F, 0.9F, 0.2F, "dyeYellow"),
	LIGHT_BLUE	("light blue",	0.6F, 0.7F, 0.95F, "dyeLightBlue"),
	MAGENTA		("magenta",		0.9F, 0.5F, 0.85F, "dyeMagenta"),
	ORANGE		("orange",		0.95F, 0.7F, 0.2F, "dyeOrange"),
	WHITE		("white",		1.0F, 1.0F, 1.0F, "dyeWhite")
	;

	private Colour(String name, float r, float g, float b, String dyeOreDictName) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.name = name;
		this.dyeOreDictName = dyeOreDictName;
	}
	
	public int dyeId() {return ordinal();}
	public int woolId() {return 15 - ordinal();}
	
	public final String name;
	public final String dyeOreDictName;
	public final float r, g, b;
	
	public static Colour getByDyeOreDictName(String n) {
		if(n == null) throw new NullPointerException();
		if(!n.startsWith("dye")) return null; // optimization
		for(Colour c : values())
			if(c.dyeOreDictName.equals(n))
				return c;
		return null;
	}
	
	private static final Colour[] VALUES = values();
	public static Colour fromDyeID(int id) {return VALUES[id];}
	public static Colour fromWoolID(int id) {return VALUES[15 - id];}
}
