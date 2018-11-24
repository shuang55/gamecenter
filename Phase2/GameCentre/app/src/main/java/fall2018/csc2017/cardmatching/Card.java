package fall2018.csc2017.cardmatching;

import android.support.annotation.NonNull;

import java.io.Serializable;

import fall2018.csc2017.R;

/**
 * A card in a card matching game.
 */
public class Card implements Comparable<Card>, Serializable {

    /**
     * The id of the card back.
     */
    private int cardBackId;

    /**
     * The id to find the card face.
     */
    private int cardFaceId;

    /**
     * The unique id.
     */
    public int id;

    /**
     * 1 if card is opened, 0 otherwise
     */
    private int opened;

    /**
     * If the card is paired.
     */
    private boolean isPaired;

    /**
     * Return the card face id.
     *
     * @return id of card face
     */
    public int getCardFaceId() {
        return cardFaceId;
    }

    /**
     * Return the id of the card back.
     *
     * @return id of card back
     */
    public int getCardBackId() {
        return cardBackId;
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
        id = backgroundId + 1;/*
        int firstCardPictureId = R.drawable.hearthstone_card1;
        cardFaceId = firstCardPictureId + backgroundId;*/
        switch ((int) Math.ceil(((double) id) / 2)) {
            case 1:
                cardFaceId = R.drawable.hearthstone_card1;
                break;
            case 2:
                cardFaceId = R.drawable.hearthstone_card2;
                break;
            case 3:
                cardFaceId = R.drawable.hearthstone_card3;
                break;
            case 4:
                cardFaceId = R.drawable.hearthstone_card4;
                break;
            case 5:
                cardFaceId = R.drawable.hearthstone_card5;
                break;
            case 6:
                cardFaceId = R.drawable.hearthstone_card6;
                break;
            case 7:
                cardFaceId = R.drawable.hearthstone_card7;
                break;
            case 8:
                cardFaceId = R.drawable.hearthstone_card8;
                break;
            case 9:
                cardFaceId = R.drawable.hearthstone_card9;
                break;
            case 10:
                cardFaceId = R.drawable.hearthstone_card10;
                break;
            case 11:
                cardFaceId = R.drawable.hearthstone_card11;
                break;
            case 12:
                cardFaceId =R.drawable.hearthstone_card12;
                break;
            default:
                cardFaceId = R.drawable.star_photo;
        }
        cardBackId = R.drawable.hearthstone_card13;
        isPaired = false;
    }

    /**
     * Get whether or not this card is isPaired.
     *
     * @return true if card is isPaired, false otherwise
     */
    boolean isPaired() {
        return isPaired;
    }

    /**
     * Set the card to isPaired or not isPaired.
     *
     * @param paired whether the card is isPaired or not
     */
    public void setPaired(boolean paired) {
        this.isPaired = paired;
    }

    /**
     * Set the card to opened or not opened;
     * <p>
     * 1 means card is opened, 0 means card is covered.
     *
     * @param opened whether the card is opened or not
     */
    void setOpened(int opened) {
        this.opened = opened;
    }

    /**
     * Get whether or not this card is opened.
     * <p>
     * 1 means card is opened, 0 means card is covered.
     *
     * @return true if card is opened, false otherwise
     */
    int isOpen() {
        return opened;
    }

    @Override
    public int compareTo(@NonNull Card o) {
        return o.id - this.id;
    }
}