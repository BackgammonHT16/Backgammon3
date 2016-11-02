/**
 * 
 */
package bg.backgammon3.model;

import java.util.LinkedHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bg.backgammon3.config.Config;
import bg.backgammon3.model.place.*;
import bg.backgammon3.model.pointstate.EndPoint;

/**
 * @author philipp
 *
 */
public class Route {
	private Logger logger = LogManager.getLogger(Route.class);

	private Integer playerId;
	LinkedHashMap<Integer, Place> places = new LinkedHashMap<Integer, Place>();

	public Route(Integer playerId, LinkedHashMap<Integer, Place> places) {
		this.playerId = playerId;
		initRoute(places);
	}

	/**
	 * Route wird gemäß Konfigurationsdatei geladen
	 */
	private void initRoute(LinkedHashMap<Integer, Place> places) {
		for (int i = 0; Config.getInteger("player" + playerId + "RoutePlace" + i) != null; i++) {
			this.places.put(i, places.get(Config.getInteger("player" + playerId + "RoutePlace" + i)));
		}
	}

	/**
	 * Ermittelt ob sich alle Spielfiguren dieses Spielers im Heimfeld befinden
	 * 
	 * @return Wahr wenn sich alle Spielfiguren dieses Spielers im Heimfeld
	 *         befinden
	 */
	private boolean allCheckersInHomeField() {
		for (int i = 1; i < Config.getInteger("player" + playerId + "HomeFieldStartsAt"); i++) {
			if (places.get(i).getPlayerId() == playerId) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Gibt an ob zu große Augenzahlen verwendet werden dürfen um das Ziel zu
	 * erreichen
	 * 
	 * @param dices
	 *            Die aktuellen Würfel für die geprüft werden soll.
	 * @return Wahr wenn zu große Augenzahlen verwendet werden dürfen um das
	 *         Ziel zu erreichen
	 */
	private boolean overReachingMoveAllowed(Dices dices) {
		for (int i = 0; i < places.size(); i++) {
			if (isLegalStartPlace(places.get(i), dices, false)) {
				return false;
			}
		}
		// TODO hier wird evtl der Fall übersehen dass alle Checker bereits weg sind bis auf einen der 
		// sich außerhalb des HomeFields befindet. Angenommen dieser würde so würfeln dass er in einem
		// Doppelzug über das Goal hinauskommt dann würde dieser Zug verboten sein. Es ist zu prüfen
		// ob dieser Zug legal ist. 
		// Nichts desto trotz könnte man diesen Zug in zwei einzelnen Schritten machen. Es ergibt sich 
		// also spielerisch kein Nachteil.
		return allCheckersInHomeField();
	}

	/**
	 * Prüft ob von dem Angegebenen Platz gestartet werden darf.</br>
	 * Dabei werden folgende Kriterien beachtet:</br>
	 * <ul>
	 * <li>Falls die Bar belegt ist, ist sie der einzige gültige Startplatz</li>
	 * <li>Der Place enthält mindestens einen Checker des Spielers</li>
	 * <li>Place ist nicht Goal</li>
	 * <li>es gibt mindestens einen Place für den isLegalEndPlace wahr
	 * ergibt</li>
	 * </ul>
	 * 
	 * @param place
	 *            Startplatz der überprüft werden soll.
	 * @param dices
	 *            Die gewürfelte Augenzahl.
	 * @return Wahr wenn es sich bei place um einen legalen Startplatz handelt.
	 */
	public boolean isLegalStartPlace(Place place, Dices dices) {
		return isLegalStartPlace(place, dices, overReachingMoveAllowed(dices));
	}

	/**
	 * Prüft ob von dem Angegebenen Platz gestartet werden darf.</br>
	 * Dabei werden folgende Kriterien beachtet:</br>
	 * <ul>
	 * <li>Falls die Bar belegt ist, ist sie der einzige gültige Startplatz</li>
	 * <li>Der Place gehört uns und ist mit mindestens einem Klotz belegt.</li>
	 * <li>Place ist nicht Goal</li>
	 * <li>es gibt mindestens einen Place für den isLegalEndPlace wahr
	 * ergibt</li>
	 * </ul>
	 * 
	 * @param place
	 *            Startplatz der überprüft werden soll.
	 * @param dices
	 *            Die gewürfelte Augenzahl.
	 * @param overReachingIsAllowed
	 *            Falls dieser Parameter wahr ist, ist das Goal auch dann noch
	 *            erreichbar wenn zu hoch gewürfelt wurde.
	 * @return Wahr wenn es sich bei place um einen legalen Startplatz handelt.
	 */
	private boolean isLegalStartPlace(Place place, Dices dices, boolean overReachingIsAllowed) {
		if (places.get(0).getNumberOfCheckers() > 0) {
			// Die Bar ist besetzt
			if (place.getId() != places.get(0).getId()) {
				return false;
			}
		}
		if (place.getPlayerId() != playerId) {
			// Es handelt sich um ein gegnerisches Feld
			return false;
		}
		if (place.getNumberOfCheckers() == 0) {
			// Das Feld ist leer
			return false;
		}
		if (place.getId() == places.get(places.size() - 1).getId()) {
			// Es handelt sich um unser Goal
			return false;
		}
		// Prüfen ob dieser Platz einen gültigen Zielplatz hat.
		if (hasLegalEndPlace(place, dices, false, overReachingIsAllowed)) {
			return true;
		}
		return false;
	}

	/**
	 * Prüft ob der gegebene Platz einen legalen Zielplatz hat und falls
	 * markRoute wahr ist wird dort dann die Route dorthin eingetragen.
	 * 
	 * @param place
	 *            Der Ausgangsplatz.
	 * @param dices
	 *            Die zur Verfügung stehenden Würfel.
	 * @param markRoute
	 *            Falls dieser Parameter wahr ist wird die Route in den Zielen
	 *            eingetragen.
	 * @return Wahr falls ein gültiger Zielplatz existiert.
	 */
	public boolean hasLegalEndPlace(Place place, Dices dices, boolean markRoute) {
		return hasLegalEndPlace(place, dices, true, overReachingMoveAllowed(dices));
	}
	
	/**
	 * Prüft ob der gegebene Platz einen legalen Zielplatz hat und falls
	 * markRoute wahr ist wird dort dann die Route dorthin eingetragen.
	 * 
	 * @param place
	 *            Der Ausgangsplatz.
	 * @param dices
	 *            Die zur Verfügung stehenden Würfel.
	 * @param markRoute
	 *            Falls dieser Parameter wahr ist wird die Route in den Zielen
	 *            eingetragen.
	 * @param overReachingIsAllowed
	 *            Falls dieser Parameter wahr ist, ist das Goal auch dann noch
	 *            erreichbar wenn zu hoch gewürfelt wurde.
	 * @return Wahr falls ein gültiger Zielplatz existiert.
	 */
	public boolean hasLegalEndPlace(Place place, Dices dices, boolean markRoute, boolean overReachingIsAllowed) {
		int d0 = (dices.getValue(0) == null) ? 0 : dices.getValue(0).getValue();
		int d1 = (dices.getValue(1) == null) ? 0 : dices.getValue(1).getValue();
		int d2 = (dices.getValue(2) == null) ? 0 : dices.getValue(2).getValue();
		int d3 = (dices.getValue(3) == null) ? 0 : dices.getValue(3).getValue();

		int pId = getPlaceId(place);
		boolean oRA = overReachingIsAllowed;
		boolean result = false;

		if (d0 != 0) {
			// Erster Würfel
			Place p0 = getPlace(d0 + pId, oRA);
			if (isLegalEndPlace(p0)) {
				result = true;
				if (markRoute) {
					getPlace(d0 + pId, oRA).setState(new EndPoint(playerId, dices.getValue(0), p0));
				}

				if (d1 != 0 && d0 + pId < places.size()) {
					// Zweiter Würfel
					Place p1 = getPlace(d0 + d1 + pId, oRA);
					if (isLegalEndPlace(p1)) {
						if (markRoute) {
							getPlace(d0 + d1 + pId, oRA).setState(new EndPoint(playerId, dices.getValue(0), p0, dices.getValue(1), p1));
						}
					}

					if (d2 != 0 && d0 + d1 + pId < places.size()) {
						// Dritter Würfel
						Place p2 = getPlace(d0 + d1 + d2 + pId, oRA);
						if (isLegalEndPlace(p2)) {
							if (markRoute) {
								getPlace(d0 + d1 + d2 + pId, oRA).setState(
										new EndPoint(playerId, dices.getValue(2), p0, dices.getValue(1), p1, dices.getValue(0), p2));
							}
						}

						if (d3 != 0 && d0 + d1 + d2 + pId < places.size()) {
							// Vierter Würfel
							Place p3 = getPlace(d0 + d1 + d2 + d3 + pId, oRA);
							if (isLegalEndPlace(p3)) {
								if (markRoute) {
									getPlace(d0 + d1 + d2 + d3 + pId, oRA).setState(new EndPoint(playerId, dices.getValue(3), p0,
											dices.getValue(2), p1, dices.getValue(1), p2, dices.getValue(0), p3));
								}
							}
						}
					}
				}
			}
		}
		if(d1 != 0 && d1 != d0){
			// Zweiter Würfel
			Place p0 = getPlace(d1 + pId, oRA);
			if (isLegalEndPlace(p0)) {
				result = true;
				if (markRoute) {
					getPlace(d1 + pId, oRA).setState(new EndPoint(playerId, dices.getValue(1), p0));
				}

				if (d0 != 0 && d1 + pId < places.size()) {
					// Zweiter Würfel
					Place p1 = getPlace(d0 + d1 + pId, oRA);
					if (isLegalEndPlace(p1)) {
						if (markRoute) {
							getPlace(d0 + d1 + pId, oRA).setState(new EndPoint(playerId, dices.getValue(1), p0, dices.getValue(0), p1));
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * Findet den Place an i-ter Stelle. Falls oRA (overReachIsAllowed) aktiviert ist 
	 * dann erhält man Goal wenn die zahl größer places.size() ist.
	 * @param i
	 * @param oRA
	 * @return
	 */
	private Place getPlace(int i, boolean oRA) {
		if (i < 0) {
			return null;
		} else if (i >= places.size() && oRA) {
			return places.get(places.size() - 1);
		} else if (i < places.size()) {
			return places.get(i);
		}
		return null;
	}

	/**
	 * Ermittelt die Position eines Place innerhalb dieser Route.
	 * 
	 * @param place
	 *            Der zu untersuchende Place.
	 * @return Position des Place in dieser Route.
	 */
	private int getPlaceId(Place place) {
		for (int i = 0; i < places.size(); i++) {
			if (places.get(i).getId() == place.getId()) {
				return i;
			}
		}
		logger.info("Place existiert nicht auf dieser Route.");
		return 0;
	}

	/**
	 * Prüft ob der gewählte Place als Ziel gewählt werden darf.</br>
	 * Dafür muss eine der folgenden Bedingungen gelten:</br>
	 * <ul>
	 * <li>Der Place ist mit maximal einem Checker eines Gegners belegt.</li>
	 * <li>Der Place ist unbelegt</li>
	 * <li>Der Place gehört dem Spieler</li>
	 * <li>Der Place ist das Goal des Spielers und allCheckersInHomeField ergibt
	 * wahr</li>
	 * </ul>
	 * Dabei muss stets gelten das der Platz nicht der Startplatz des Spielers
	 * ist.
	 * 
	 * @param place
	 *            Der Place der als Zielplatz überprüft werden soll.
	 * @return Wahr wenn es sich bei place um einen legalen Zielplatz handelt.
	 */
	public boolean isLegalEndPlace(Place place) {
		if(place == null) {
			return false;
		}
		if(place.getPlayerId() != playerId && place.getPlayerId() != -1) {
			// Platz ist vom Gegner belegt
			if(place.getNumberOfCheckers() <= 1) {
				// Der Platz ist durch weniger als einen Gegnerischen Checker blockiert.
				return true;
			}
		} else if(place.getPlayerId() == -1) {
			// Der Platz ist unbelegt
			return true;
		} else if(place.getPlayerId() == playerId && place instanceof Point) {
			// Der Platz gehört uns ist aber weder unser Goal noch unsere Bar
			return true;
		} else if(place.getPlayerId() == playerId && place instanceof Goal && allCheckersInHomeField()) {
			// Wenn alle Checker im Heimfeld sind kann unser Tor freigeschalten werden.
			return true;
		}
		return false;
	}

	/**
	 * Falls routePositionId innerhalb der Route liegt wird direkt
	 * isLegalEndPlace mit dem entsprechenden Place aufgerufen. Falls der Wert
	 * außerhalb der Route liegt wird geprüft ob overReachingMoveAllowed wahr
	 * ergibt. Falls ja, wird isLegalEndPlace mit dem Goal des Spielers
	 * aufgerufen
	 * 
	 * @param routePositionId
	 * @return
	 */
	public boolean isLegalEndPlace(Integer routePositionId) {
		Place p = getPlace(routePositionId, false);
		return isLegalEndPlace(p);
	}

	/**
	 * Falls routePositionId innerhalb der Route liegt wird direkt
	 * isLegalEndPlace mit dem entsprechenden Place aufgerufen. Falls der Wert
	 * außerhalb der Route liegt wird geprüft ob overReachingMoveAllowed wahr
	 * ergibt. Falls ja, wird isLegalEndPlace mit dem Goal des Spielers
	 * aufgerufen
	 * 
	 * @param routePositionId
	 * @param overReachingIsAllowed
	 *            Falls dieser Parameter wahr ist, ist das Goal auch dann noch
	 *            erreichbar wenn zu hoch gewürfelt wurde.
	 * @return
	 */
	public boolean isLegalEndPlace(Integer routePositionId, boolean overReachingIsAllowed) {
		Place p = getPlace(routePositionId, overReachingIsAllowed);
		return isLegalEndPlace(p);
	}

	public Place getBar() {
		return places.get(0);
	}

	public Integer getBarId() {
		return places.get(0).getId();
	}
	
	/**
	 * Gibt wahr zurück wenn alle Checker des Spielers im letzten Feld sind.
	 * @return Wahr wenn der Spieler gewonnen hat.
	 */
	public boolean hasWon() {
		boolean won = true;
		for(int i = 0; i < places.size() - 1; i++) {
			if(places.get(i).getPlayerId() == playerId && places.get(i).getNumberOfCheckers() > 0) {
				won = false;
			}
		}
		return won;
	}
}
