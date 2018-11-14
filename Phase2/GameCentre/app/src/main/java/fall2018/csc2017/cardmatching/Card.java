package fall2018.csc2017.cardmatching;

import android.support.annotation.NonNull;

import java.io.Serializable;

import fall2018.csc2017.R;

/**
 * A card in a card matching game.
 */
public class Card implements Comparable<Card>, Serializable {

    /**
     * The id to find the card back.
     */
    private int cardBack;

    /**
     * The id to find the card face.
     */
    private int cardFace;

    /**
     * The unique id.
     */
    public int id;

    /**
     * If the card is paired.
     */
    private boolean paired;

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public int getcardFace() {
        return cardFace;
    }

    /**
     * Return the card id.
     *
     * @return the tile id
     */
    public int getId() {
        return id;
    }

    /**
     * A card with a background id; look up and set the id.
     *
     * @param backgroundId ID of the background
     */
    Card(int backgroundId) {
        id = backgroundId + 1;
        switch ((int) Math.ceil(((double) id)/2)) {
            case 1:
                cardFace = R.drawable.tile_1;
                break;
            case 2:
                cardFace = R.drawable.tile_2;
                break;
            case 3:
                cardFace = R.drawable.tile_3;
                break;
            case 4:
                cardFace = R.drawable.tile_4;
                break;
            case 5:
                cardFace = R.drawable.tile_5;
                break;
            case 6:
                cardFace = R.drawable.tile_6;
                break;
            case 7:
                cardFace = R.drawable.tile_7;
                break;
            case 8:
                cardFace = R.drawable.tile_8;
                break;
            case 9:
                cardFace = R.drawable.tile_9;
                break;
            case 10:
                cardFace = R.drawable.tile_10;
                break;
            case 11:
                cardFace = R.drawable.tile_11;
                break;
            case 12:
                cardFace = R.drawable.tile_12;
                break;
//            case 13:
//                cardFace = R.drawable.tile_13;
//                break;
//            case 14:
//                cardFace = R.drawable.tile_14;
//                break;
//            case 15:
//                cardFace = R.drawable.tile_15;
//                break;
//            case 16:
//                cardFace = R.drawable.tile_16;
//                break;
//            case 17:
//                cardFace = R.drawable.tile_17;
//                break;
//            case 18:
//                cardFace = R.drawable.tile_18;
//                break;
//            case 19:
//                cardFace = R.drawable.tile_19;
//                break;
//            case 20:
//                cardFace = R.drawable.tile_20;
//                break;
//            case 21:
//                cardFace = R.drawable.tile_21;
//                break;
//            case 22:
//                cardFace = R.drawable.tile_22;
//                break;
//            case 23:
//                cardFace = R.drawable.tile_23;
//                break;
//            case 24:
//                cardFace = R.drawable.tile_24;
//                break;
//            case 25:
//                cardFace = R.drawable.blank_tile;
//                break;
            default:
                cardFace = R.drawable.blank_tile;
        }
        cardBack = R.drawable.star_photo;
        paired = false;
    }

    boolean isPaired(){
        return paired;
    }

    @Override
    public int compareTo(@NonNull Card o) {
        return o.id - this.id;
    }
}