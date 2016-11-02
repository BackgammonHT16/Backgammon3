/**
 * 
 */
package bg.backgammon3.view.place;

import bg.backgammon3.config.Config;
import bg.backgammon3.model.place.Place;
import bg.backgammon3.view.helper.StaticImageHelper;

/**
 * @author philipp
 *
 */
public class GoalView extends PlaceView{

	public GoalView(Place place) {
		super(place);
		initGoalView();
		initPlaceView();
	}

	private void initGoalView() {
		StaticImageHelper.loadImageView(Config.getString("goal" + place.getPlayerId() + "Image"), Config.getInteger("goalWidth"), true,
				Config.getInteger("place" + place.getId() + "PositionX"),
				Config.getInteger("place" + place.getId() + "PositionY"), this);
		setRotate(Config.getInteger("place" + place.getId() + "Rotation"));
		normalImage = getImage();
		
		// Bilder Laden
		for(int i = 0; Config.getString("goalImageStartPlayer" + i) != null; i++) {
			startImages.add(StaticImageHelper.loadImage(Config.getString("goalImageStartPlayer" + i)));
			endImages.add(StaticImageHelper.loadImage(Config.getString("goalImageEndPlayer" + i)));
		}
	}
}
