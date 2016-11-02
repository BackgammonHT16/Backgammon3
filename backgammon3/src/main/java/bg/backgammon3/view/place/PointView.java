/**
 * 
 */
package bg.backgammon3.view.place;

import bg.backgammon3.config.Config;
import bg.backgammon3.model.place.Place;
import bg.backgammon3.view.helper.*;

/**
 * @author philipp
 *
 */
public class PointView extends PlaceView {

	public PointView(Place place) {
		super(place);
		initPointView();
		initPlaceView();
	}

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
		}
	}

}
