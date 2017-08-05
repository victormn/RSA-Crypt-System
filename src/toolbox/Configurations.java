package toolbox;

public class Configurations {
    
    private int SIZEIMG;
    private String NAME_IMGOUT;
    private String NAME_MSNOUT;

    public Configurations(int SIZEIMG, String NAME_IMGOUT, String NAME_MSNOUT) {
        this.SIZEIMG = SIZEIMG;
        this.NAME_IMGOUT = NAME_IMGOUT;
        this.NAME_MSNOUT = NAME_MSNOUT;
    }

    public int getSIZEIMG() {
        return SIZEIMG;
    }

    public String getNAME_IMGOUT() {
        return NAME_IMGOUT;
    }

    public String getNAME_MSNOUT() {
        return NAME_MSNOUT;
    }
    
    
}
