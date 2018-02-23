package packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.omg.IOP.ExceptionDetailMessage;




public class packets{
	
	public interface packet extends Serializable{
		 default String bok() {
			return "yara";
		}
	}
	
	public static byte[] pack(Object obj) {
		try {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ObjectOutputStream os=null;
		
			os = new ObjectOutputStream(out);
		    os.writeObject(obj);
		    return out.toByteArray();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		
	}
	public static Object upack(byte[] data) {
		 
			try {
				ByteArrayInputStream in = new ByteArrayInputStream(data);
			    ObjectInputStream is;
				
					is = new ObjectInputStream(in);
				
					return is.readObject();
				
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
	}
}


 