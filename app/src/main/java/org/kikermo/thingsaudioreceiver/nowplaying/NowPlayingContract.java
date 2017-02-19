package org.kikermo.thingsaudioreceiver.nowplaying;

import org.kikermo.thingsaudioreceiver.BasePresenter;
import org.kikermo.thingsaudioreceiver.BaseView;
import org.kikermo.thingsaudioreceiver.model.data.PlayPosition;
import org.kikermo.thingsaudioreceiver.model.data.PlayState;
import org.kikermo.thingsaudioreceiver.model.data.Track;

/**
 * Created by EnriqueR on 17/02/2017.
 */

public interface NowPlayingContract {
    public interface View extends BaseView<Presenter> {

        void updateTrack(Track track);

        void updatePosition(PlayPosition position);

        void updatePlayState(PlayState playState);

    }

    public interface Presenter extends BasePresenter {

    }
}
