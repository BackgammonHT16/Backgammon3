/**
 * 
 */
package bg.backgammon3.view.place;

import bg.backgammon3.config.Config;
import bg.backgammon3.model.place.Place;
import bg.backgammon3.model.pointstate.EndPoint;
import bg.backgammon3.model.pointstate.StartPoint;
import bg.backgammon3.view.helper.*;

/**
 * Die Point View
 *
 */
public class PointView extends PlaceView {

	/**
	 * Konstruktor
	 * @param place der platz
	 */
	public PointView(Place place) {
		super(place);
		initPointView();
		initPlaceView();
	}

	/**
	 * Initialisiert die PointView
	 */
	private void initPointView() {
		StaticImageHelper.loadImageView(Config.getString("pointImage"), Config.getInteger("pointWidth"), true,
				Config.getInteger("place" + place.getId() + "PositionX"),
				Config.getInteger("place" + place.getId() + "PositionY"), this);
		setRotate(Config.getInteger("place" + place.getId() + "Rotation"));
		normalImage = getImage();
		
		// Bilder Laden
		for(int i = 0; Config.getString("pointImageStartPlayer" + i) != null; i++) {
			startImages.add(StaticImageHelper.loadImage(Config.getString("pointImageStartPlayer" + i)));
			endImages.add(StaticImageHelper.loadImage(Config.getString("pointImageEndPlayer" + i)));
			hoverStartImages.add(StaticImageHelper.loadImage(Config.getString("pointImageHoverStartPlayer" + i)));
			hoverEndImages.add(StaticImageHelper.loadImage(Config.getString("pointImageHoverEndPlayer" + i)));
		}
	}
	

	public void update(boolean showHighlights) {
		if(place.getState() instanceof StartPoint && showHighlights) {
			if(place.getState().getSelected()) {
				setImage(hoverStartImages.get(((StartPoint)place.getState()).getPlayerId()));
			} else {
				setImage(startImages.get(((StartPoint)place.getState()).getPlayerId()));
			}
			this.toFront();
		} else if(place.getState() instanceof EndPoint && showHighlights) {
			if(place.getState().getSelected()) {
				setImage(hoverEndImages.get(((EndPoint)place.getState()).getPlayerId()));
			} else {
				setImage(endImages.get(((EndPoint)place.getState()).getPlayerId()));
			}
			this.toFront();
		} else {
			setImage(normalImage);
		}
		tempImage = this.getImage();
		tempPointState = place.getState();
		tempPlayerId = place.getState().getPlayerId();
		consistencyCheck();
	}

}
