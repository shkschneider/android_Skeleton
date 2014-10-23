package me.shkschneider.skeleton;

/**
 * @TODO
 */
public class WebService {

//        Ion.with(MainActivity.this)
//                .load(URL)
//                .asJsonObject()
//                .withResponse()
//                .setCallback(new FutureCallback<Response<JsonObject>>() {
//                    @Override
//                    public void onCompleted(final Exception e, final Response<JsonObject> result) {
//                        loading(false);
//                        if (e != null) {
//                            LogHelper.wtf(e);
//                            ActivityHelper.croutonRed(MainActivity.this, e.getLocalizedMessage());
//                            return;
//                        }
//
//                        final int responseCode = result.getHeaders().getResponseCode();
//                        final String responseMessage = result.getHeaders().getResponseMessage();
//                        if (responseCode >= 400) {
//                            ActivityHelper.croutonOrange(MainActivity.this, String.format("%d: %s", responseCode, responseMessage));
//                        } else {
//                            ActivityHelper.croutonGreen(MainActivity.this, String.format("%d: %s", responseCode, responseMessage));
//                        }
//
//                        mAdapter.clear();
//                        final JsonObject jsonObject = result.getResult();
//                        for (final String key : GsonParser.keys(jsonObject)) {
//                            final String value = GsonParser.string(jsonObject, key);
//                            if (! StringHelper.nullOrEmpty(value)) {
//                                mAdapter.add(key + " " + value);
//                            }
//                        }
//                        mAdapter.notifyDataSetChanged();
//                    }
//                });

}
