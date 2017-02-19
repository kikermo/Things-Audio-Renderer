package org.kikermo.thingsaudioreceiver;

/**
 * Created by EnriqueR on 17/02/2017.
 */

public interface BaseView<T extends BasePresenter> {
    void setBasePresenter(T presenter);
}
