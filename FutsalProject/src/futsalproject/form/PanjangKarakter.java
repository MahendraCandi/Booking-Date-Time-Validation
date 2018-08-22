package futsalproject.form;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class PanjangKarakter extends PlainDocument{
    private int limit;
    
    public PanjangKarakter(int limitation){
        this.limit=limitation;
    }
    
    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException{
        if(str==null){
            return;
        }
        
        if((getLength() + str.length()) <= limit){
            super.insertString(offset, str, attr);
        }
        
    }
}
