/**
 * 
 */
package bg.backgammon3.view.place;

import bg.backgammon3.config.Config;
import bg.backgammon3.model.place.Place;
import bg.backgammon3.model.pointstate.EndPoint;
import bg.backgammon3.model.pointstate.StartPoint;
import bg.backgammon3.view.helper.Position;
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
	
	@Override
	public Position getNewCheckerPosition() {
		
		double x0 = 1.0 * Config.getInteger("checkerWidth") * (int)((checkers.size()-0) % 3 - 1);
		double y0 = 1.0 * Config.getInteger("checkerWidth") * (int)((checkers.size()-0) / 3 - 2);

		double angle = -Math.toRadians(getRotate());
		
		double x1 = Math.cos(angle) * x0 + Math.sin(angle) * y0;
		double y1 = - Math.sin(angle) * x0 + Math.cos(angle) * y0;

		double x = getTranslateX() + x1;
		double y = getTranslateY() + y1;
		
		return new Position(x, y, getRotate());
	}
	

	public void update(boolean showHighlights) {
		if(place.getState() instanceof StartPoint && showHighlights) {
			setImage(startImages.get(((StartPoint)place.getState()).getPlayerId()));
		} else if(place.getState() instanceof EndPoint && showHighlights) {
			setImage(endImages.get(((EndPoint)place.getState()).getPlayerId()));
		} else {
			setImage(normalImage);
		}
	}
}
