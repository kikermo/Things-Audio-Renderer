package org.kikermo.thingsaudioreceiver.model;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * from https://gist.github.com/zsxwing/9183098
 */

public final class RxBroadcastReceiver {

    public static class IntentWithContext {
        private Context context;
        private Intent intent;

        public IntentWithContext(Context context, Intent intent) {
            this.context = context;
            this.intent = intent;
        }

        public Context getContext() {
            return context;
        }

        public Intent getIntent() {
            return intent;
        }
    }

    public static Observable<Intent> fromBroadcast(final Context context, final IntentFilter filter) {
        return Observable.create(e -> {


            final BroadcastReceiver receiver = new BroadcastReceiver() {

                @Override
                public void onReceive(Context context1, Intent intent) {
                    e.onNext(intent);
                }

            };
            context.registerReceiver(receiver, filter);
            e.setDisposable(new Disposable() {
                @Override
                public void dispose() {
                    context.unregisterReceiver(receiver);
                }

                @Override
                public boolean isDisposed() {
                    return false;
                }
            });
        });
    }
}