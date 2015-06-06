package mods.immibis.core.api.util;

import java.io.Serializable;


public final class XYZ implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public final int x, y, z;
	public XYZ(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public boolean equals(Object o) {
		try {
			XYZ xyz = (XYZ)o;
			return x == xyz.x && y == xyz.y && z == xyz.z;
		} catch(ClassCastException e) {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return ((x + 8192) + (z+8192) * 16903) * 256 + y;
	}
	
	@Override
	public String toString() {
		return "["+x+","+y+","+z+"]";
	}

	public XYZ step(int direction) {
		switch(direction) {
		case Dir.NX: return new XYZ(x - 1, y, z);
		case Dir.PX: return new XYZ(x + 1, y, z);
		case Dir.NY: return new XYZ(x, y - 1, z);
		case Dir.PY: return new XYZ(x, y + 1, z);
		case Dir.NZ: return new XYZ(x, y, z - 1);
		case Dir.PZ: return new XYZ(x, y, z + 1);
		default: throw new IllegalArgumentException("Invalid direction "+direction);
		}
	}
}
