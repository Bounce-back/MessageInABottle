package io.github.bounceback.messageinabottle;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayListDataType implements PersistentDataType<byte[],List<String>>{
	
	private Charset charset=StandardCharsets.US_ASCII;
	
	public Class<byte[]> getPrimitiveType() {
		return byte[].class;
	}

	@SuppressWarnings("unchecked")
	public Class<List<String>> getComplexType() {
		return (Class<List<String>>) (new ArrayList<String>()).getClass();
	}

	public byte[] toPrimitive(List<String> complex, PersistentDataAdapterContext context) {
		byte[][] allStringBytes=new byte[complex.size()][];
		int total=0;
		for (int i=0;i<allStringBytes.length;i++) {
			byte[] bytes=complex.get(i).getBytes(charset);
			allStringBytes[i]=bytes;
			total+=bytes.length;
		}
		
		ByteBuffer buffer=ByteBuffer.allocate(total+allStringBytes.length*4);
		for (byte[] bytes:allStringBytes) {
			buffer.putInt(bytes.length);
			buffer.put(bytes);
		}
		
		return buffer.array();
	}

	public List<String> fromPrimitive(byte[] primitive, PersistentDataAdapterContext context) {
		ByteBuffer buffer=ByteBuffer.wrap(primitive);
		ArrayList<String> list=new ArrayList<String>();
		
		while(buffer.remaining()>0) {
			if(buffer.remaining()<4) break;
			int stringLength=buffer.getInt();
			if (buffer.remaining()<stringLength) break;
			
			byte[] stringBytes=new byte[stringLength];
			buffer.get(stringBytes);
			
			list.add(new String(stringBytes,charset));
		}
		
		return Arrays.asList(list.toArray(new String[list.size()]));
	}

}
