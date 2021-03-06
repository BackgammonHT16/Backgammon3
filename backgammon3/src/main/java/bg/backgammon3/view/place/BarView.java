/**
 * 
 */
package bg.backgammon3.view.place;


import bg.backgammon3.config.Config;
import bg.backgammon3.model.place.Place;
import bg.backgammon3.model.pointstate.EndPoint;
import bg.backgammon3.model.pointstate.StartPoint;
import bg.backgammon3.view.helper.StaticImageHelper;

/**
 * 
 *
 */
public class BarView extends PlaceView {

	public BarView(Place place) {
		super(place);
		initBarView();
		initPlaceView();
	}

	private void initBarView() {
		StaticImageHelper.loadImageView(Config.getString("bar" + place.getPlayerId() + "Image"), Config.getInteger("barWidth"), true,
				Config.getInteger("place" + place.getId() + "PositionX"),
				Config.getInteger("place" + place.getId() + "PositionY"), this);
		setRotate(Config.getInteger("place" + place.getId() + "Rotation"));
		normalImage = getImage();
		
		// Bilder Laden
		for(int i = 0; Config.getString("barImageStartPlayer" + i) != null; i++) {
			startImages.add(StaticImageHelper.loadImage(Config.getString("barImageStartPlayer" + i)));
			endImages.add(StaticImageHelper.loadImage(Config.getString("barImageEndPlayer" + i)));
			hoverStartImages.add(StaticImageHelper.loadImage(Config.getString("barImageHoverStartPlayer" + i)));
			hoverEndImages.add(StaticImageHelper.loadImage(Config.getString("barImageHoverEndPlayer" + i)));
		}
	}

	public void update(boolean showHighlights) {
		if(place.getState() instanceof StartPoint && showHighlights) {
			if(place.getState().getSelected()) {
				setImage(hoverStartImages.get(((StartPoint)place.getState()).getPlayerId()));
			} else {
				setImage(startImages.get(((StartPoint)place.getState()).getPlayerId()));
			}
		} else if(place.getState() instanceof EndPoint && showHighlights) {
			if(place.getState().getSelected()) {
				setImage(hoverEndImages.get(((EndPoint)place.getState()).getPlayerId()));
			} else {
				setImage(hoverEndImages.get(((EndPoint)place.getState()).getPlayerId()));
			}
		} else {
			setImage(normalImage);
		}
		tempImage = this.getImage();
		tempPointState = place.getState();
		tempPlayerId = place.getState().getPlayerId();
		consistencyCheck();
	}
}
