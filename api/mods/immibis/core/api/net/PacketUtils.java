package mods.immibis.core.api.net;

import io.netty.buffer.ByteBuf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;

public class PacketUtils {

	public static ItemStack readItemStack(DataInputStream in) throws IOException {
		short id = in.readShort();
		if(id < 0) return null;
		
		byte count = in.readByte();
		short damage = in.readShort();
		ItemStack stack = new ItemStack(Item.getItemById(id), count, damage);
		stack.stackTagCompound = readNBT(in);
		return stack;
	}
	
	public static ItemStack readItemStack(ByteBuf in) throws IOException {
		short id = in.readShort();
		if(id < 0) return null;
		
		byte count = in.readByte();
		short damage = in.readShort();
		ItemStack stack = new ItemStack(Item.getItemById(id), count, damage);
		stack.stackTagCompound = readNBT(in);
		return stack;
	}

	public static void writeItemStack(ItemStack is, DataOutputStream out) throws IOException {
		if (is == null)
            out.writeShort(-1);
        else {
            out.writeShort(Item.getIdFromItem(is.getItem()));
            out.writeByte(is.stackSize);
            out.writeShort(is.getItemDamage());
            if(is.getItem().isDamageable() || is.getItem().getShareTag())
				writeNBT(is.stackTagCompound, out);
            else
            	writeNBT(null, out);
        }
	}
	
	public static void writeItemStack(ItemStack is, ByteBuf out) throws IOException {
		if (is == null)
            out.writeShort(-1);
        else {
            out.writeShort(Item.getIdFromItem(is.getItem()));
            out.writeByte(is.stackSize);
            out.writeShort(is.getItemDamage());
            if(is.getItem().isDamageable() || is.getItem().getShareTag())
				writeNBT(is.stackTagCompound, out);
            else
            	writeNBT(null, out);
        }
	}
	
	public static void writeNBT(NBTTagCompound tag, DataOutputStream out) throws IOException {
		if (tag == null)
			out.writeShort(-1);
        else {
            byte[] bytes = CompressedStreamTools.compress(tag);
            out.writeShort((short)bytes.length);
            out.write(bytes);
        }
	}
	
	public static void writeNBT(NBTTagCompound tag, ByteBuf out) throws IOException {
		if (tag == null)
			out.writeShort(-1);
        else {
            byte[] bytes = CompressedStreamTools.compress(tag);
            out.writeShort((short)bytes.length);
            out.writeBytes(bytes);
        }
	}
	
	public static NBTTagCompound readNBT(DataInputStream in) throws IOException {
        short length = in.readShort();

        if (length < 0)
            return null;
        else {
            byte[] bytes = new byte[length];
            in.readFully(bytes);
            return CompressedStreamTools.func_152457_a(bytes, new NBTSizeTracker(Long.MAX_VALUE));
        }
    }
	
	public static NBTTagCompound readNBT(ByteBuf in) throws IOException {
        short length = in.readShort();

        if (length < 0)
            return null;
        else {
            byte[] bytes = new byte[length];
            in.readBytes(bytes);
            return CompressedStreamTools.func_152457_a(bytes, new NBTSizeTracker(Long.MAX_VALUE));
        }
    }

}
