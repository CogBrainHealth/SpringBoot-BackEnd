package server.brainboost.src.game.dto;

public interface GamePageInterface {

    Long getRestaurantId();
    String getRestaurantName();
    String getMainImg();

    Integer getCountHeart();
    Integer getCountBookmark();
    Integer getCountReview();
    Double getAverageStar();
    String getMenuNames();


}
