package core.timer;

import core.logger.Logger;
import core.logger.TagLogger;
import javafx.scene.image.Image;

import java.util.List;

/// A collection of frames which can be retrieved relative to the {@code frameTimeMs} and the given current time. In order.
public class AnimationTimer {
    private List<Image> frames;
    private int currentFrameIndex = 0;
    private double frameTimeMs;
    private long previousFrameChangeTimeNs = -1;

    private Logger log;

    /// @param frames of the animation, in order. This list will be copied over. Cant be null.
    /// @param frameTimeMs the display duration of each frame, in milliseconds.
    /// @param logTag combined with the class name, is used as a tag for logging. This can be used to determine which animation the log is coming from.
    public AnimationTimer(List<Image> frames, double frameTimeMs, String logTag){
        log = new TagLogger(this.getClass().getSimpleName() + " [" + logTag + "]");

        this.frames = List.copyOf(frames); // Image can be assumed to be immutable (https://www.pragmaticcoding.ca/javafx/elements/imageview)
        this.frameTimeMs = frameTimeMs;
    }


    /// Returns the frame that should be displayed at the given time, advancing the
    /// animation if enough time has passed since the last frame change.
    ///
    /// If the time elapsed since the last frame change is greater than or equal
    /// to the configured frame time, the reference time is reset to {@code nowNs} and the next frame is returned.
    /// Otherwise, the current frame is returned unchanged.
    ///
    /// @param nowNs the current time in nanoseconds, values less than or equal to {@code 0} are considered invalid
    /// @return the {@link Image} to display at the given time. Never advances more than one frame per call.
    public Image getFrame(long nowNs){
        // nowNs has bad data
        if(nowNs <= 0){
            log.logWarning("nowNs <= 0. Invalid data! Returning current frame.");

            return getCurrentFrame();
        }

        // If previous time has not been set -> first time calling this method
        if(previousFrameChangeTimeNs <= 0){
            previousFrameChangeTimeNs = nowNs;

            return getCurrentFrame();
        }

        // Time passed since previous frame change
        double timePassedMs = (nowNs - previousFrameChangeTimeNs) / 1E6;

        // If frame time has been exceeded -> get the next frame
        if(timePassedMs >= frameTimeMs){
            previousFrameChangeTimeNs = nowNs;

            return getNextFrame();
        }

        return getCurrentFrame();
    }

    /// Get the current frame of the animation. Based on the {@link currentFrameIndex};
    /// With bounds-checking.
    /// <p>
    /// If {@link currentFrameIndex} is out of bounds, will it be reset to 0.
    /// @return the current frame.
    private Image getCurrentFrame(){
        return frames.get(currentFrameIndex);
    }

    /// Get the next frame in the animation.
    /// <p>
    /// This updates the {@link currentFrameIndex}. With bounds-checking. Warping back to 0 when reaching its bound.
    /// @return the next frame.
    private Image getNextFrame(){
        // Increment index and check if reached bound.
        // If so, reset to zero.
        currentFrameIndex++;
        if(currentFrameIndex < 0 || currentFrameIndex >= frames.size()){
            currentFrameIndex = 0;
        }

        return frames.get(currentFrameIndex);
    }

    /// Reset animation to the first frame.
    public void reset(){
        currentFrameIndex = 0;
    }

    /// Get a copy of the frames given at creation time.
    /// @return a list of {@code Images}
    public List<Image> getFrames() {
        return List.copyOf(frames);
    }

    /// Set the frames of this animation.
    /// @param frames the collection of images which represent the frames of the animation, in order. This list will be copied over.
    public void setFrames(List<Image> frames) {
        this.frames = List.copyOf(frames);
    }
}
