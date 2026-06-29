package bms.player.beatoraja.skin;

import java.util.Optional;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import bms.player.beatoraja.MainState;
import bms.player.beatoraja.play.bga.FFmpegProcessor;

/**
 * スキンのソースイメージ(ムービー)
 *
 * @author exch
 */
public class SkinSourceMovie extends SkinSource {

	/**
	 * イメージ
	 */
	private FFmpegProcessor image;
	
	private boolean playing;
	private long starttime;

	private final int timer;
	
	private final TextureRegion region = new TextureRegion();

	public SkinSourceMovie(String s) {
		this(s, 0);
	}

	public SkinSourceMovie(String s, int timer) {
		image = new FFmpegProcessor(1);
		image.create(s);
		this.timer = timer;
		starttime = Long.MIN_VALUE;
	}

	public boolean validate() {
		return true;
	}
	
	public TextureRegion getImage(long time, MainState state) {
		if(!playing) {
			starttime = time;
			image.play(0, true);
			playing = true;
		}
		final long movietime = Math.max(time - starttime, 0);
		Texture tex = image.getFrame(movietime);
		if(tex != null) {
			region.setTexture(tex);
			region.setRegion(tex);
			return region;
		}
		return null;
	}

	public void dispose() {
    	if(isNotDisposed()) {
    		Optional.ofNullable(image).ifPresent(FFmpegProcessor::dispose);
    		setDisposed();
    	}
	}
}
