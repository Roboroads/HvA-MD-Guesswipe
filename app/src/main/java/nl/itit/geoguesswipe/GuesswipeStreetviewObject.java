package nl.itit.geoguesswipe;

/**
 * Created by rschepers on 15/04/2018.
 */


public class GuesswipeStreetviewObject {

    private int streetviewImage;
    private Boolean isInEurope;

    public static final Boolean[] PRE_DEF_ID_IS_IN_EUROPE = {
            true, //0
            false, //1
            false, //2
            true, //3
            false, //4
            true, //5
            true, //6
            false, //7
    };
    public static final int[] PRE_DEF_ID_TO_IMAGE = {
            R.drawable.img1_yes_denmark, //0
            R.drawable.img2_no_canada, //1
            R.drawable.img3_no_bangladesh, //2
            R.drawable.img4_yes_kazachstan, //3
            R.drawable.img5_no_colombia, //4
            R.drawable.img6_yes_poland, //5
            R.drawable.img7_yes_malta, //6
            R.drawable.img8_no_thailand, //7
    };

    public GuesswipeStreetviewObject(int arrId) {
        this.streetviewImage = PRE_DEF_ID_TO_IMAGE[arrId];
        this.isInEurope = PRE_DEF_ID_IS_IN_EUROPE[arrId];
    }

    public int getStreetviewImage() {
        return streetviewImage;
    }

    public Boolean getIsInEurope() {
        return isInEurope;
    }
}
