package com.google.api.games;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;
import com.google.example.games.basegameutils.GameHelper;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.Players;

public class Context extends FREContext implements
		GameHelper.GameHelperListener {
	
	private class SignIn{
		static final String SUCCESS = "SIGN_IN_SUCCESS";
		static final String FAIL = "SIGN_IN_FAIL";
		
	}

	@Override
	public void dispose() {
		SignInActivity.mHelper.onStop();
	}

	@Override
	public Map<String, FREFunction> getFunctions() {
		Map<String, FREFunction> map = new HashMap<String, FREFunction>();

		map.put("Leaderboards.submitScore", new Leaderboards.submitScore());
		map.put("Leaderboards.showAll", new Leaderboards.showAll());
		map.put("Leaderboards.show", new Leaderboards.show());

		map.put("Achievements.reveal", new Achievements.reveal());
		map.put("Achievements.unlock", new Achievements.unlock());
		map.put("Achievements.increment", new Achievements.increment());
		map.put("Achievements.show", new Achievements.show());

		map.put("beginUserInitiatedSignIn", new beginUserInitiatedSignIn());
		map.put("signOut", new signOut());
		map.put("promptUserToSignInOnStartup",
				new promptUserToSignInOnStartup());
		map.put("isSignedIn",
				new isSignedIn());
		map.put("start",
				new start());
		map.put("getPlayerID", new getPlayerID());
		map.put("getDisplayName", new getDisplayName());
		map.put("getHiResImageUri", new getHiResImageUri());
		map.put("getIconImageUri", new getIconImageUri());

		return map;
	}

	public static class beginUserInitiatedSignIn implements FREFunction {

		@Override
		public FREObject call(FREContext context, FREObject[] args) {
			SignInActivity.mHelper.beginUserInitiatedSignIn();
			return null;
		}

	}
	
	public static class isSignedIn implements FREFunction {

		@Override
		public FREObject call(FREContext context, FREObject[] args) {
			FREObject bool = null;
			try {
				 bool = FREObject.newObject(SignInActivity.mHelper.isSignedIn()) ;
			} catch (FREWrongThreadException e) {
				e.printStackTrace();
			}
			
			return bool;
		}

	}
	public static class getPlayerID implements FREFunction {

		@Override
		public FREObject call(FREContext context, FREObject[] args) {
			FREObject bool = null;
			try {
				 Player player = Games.Players.getCurrentPlayer(SignInActivity.mHelper.getApiClient());
				 bool = FREObject.newObject(player.getPlayerId());
			} catch (FREWrongThreadException e) {
				e.printStackTrace();
			}
			
			return bool;
		}

	}
	public static class getDisplayName implements FREFunction {

		@Override
		public FREObject call(FREContext context, FREObject[] args) {
			FREObject res = null;
			try {
				 Player player = Games.Players.getCurrentPlayer(SignInActivity.mHelper.getApiClient());
				 res = FREObject.newObject(player.getDisplayName());
			} catch (FREWrongThreadException e) {
				e.printStackTrace();
			}
			
			return res;
		}

	}
	public static class getHiResImageUri implements FREFunction {

		@Override
		public FREObject call(FREContext context, FREObject[] args) {
			FREObject res = null;
			try {
				 Player player = Games.Players.getCurrentPlayer(SignInActivity.mHelper.getApiClient());
				 if (player.hasHiResImage()) {
				 	res = FREObject.newObject(player.getHiResImageUri().getSchemeSpecificPart());
				 }
			} catch (FREWrongThreadException e) {
				e.printStackTrace();
			}
			
			return res;
		}

	}
	public static class getIconImageUri implements FREFunction {

		@Override
		public FREObject call(FREContext context, FREObject[] args) {
			FREObject res = null;
			try {
				 Player player = Games.Players.getCurrentPlayer(SignInActivity.mHelper.getApiClient());
				 if (player.hasIconImage()) {
				 	res = FREObject.newObject(player.getIconImageUri().getSchemeSpecificPart());
				 }
			} catch (FREWrongThreadException e) {
				e.printStackTrace();
			}
			
			return res;
		}

	}

	public static class promptUserToSignInOnStartup implements FREFunction {

		@Override
		public FREObject call(FREContext context, FREObject[] args) {

			try {
				Extension.autoSignIn = args[0].getAsBool();
			} catch (IllegalStateException e) {

				e.printStackTrace();
			} catch (FRETypeMismatchException e) {

				e.printStackTrace();
			} catch (FREInvalidObjectException e) {

				e.printStackTrace();
			} catch (FREWrongThreadException e) {

				e.printStackTrace();
			}

			return null;
		}

	}

	public static class start implements FREFunction {

		@Override
		public FREObject call(FREContext context, FREObject[] args) {

			Intent sign = new Intent(context.getActivity(),
					SignInActivity.class);
			context.getActivity().startActivity(sign);
			return null;
		}

	}

	public static class signOut implements FREFunction {

		@Override
		public FREObject call(FREContext context, FREObject[] args) {
			SignInActivity.mHelper.signOut();
			return null;
		}

	}

	@Override
	public void onSignInFailed() {
		dispatchStatusEventAsync(SignIn.FAIL, "");

	}

	@Override
	public void onSignInSucceeded() {
		dispatchStatusEventAsync(SignIn.SUCCESS, "");

	}
	
	

}
