package core.logger;

public class TagLogger implements Logger {
    private final String tag;

    public TagLogger(String tag){
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    private void Log(String level, String tag, String msg){
        System.out.printf(
                "[%s] (%s): %s\n",
                level,
                tag,
                msg
        );
    }

    @Override
    public void logInfo(String msg) {
        Log("INFO", tag, msg);
    }

    @Override
    public void logWarning(String msg) {
        Log("WARNING", tag, msg);
    }

    @Override
    public void logError(String msg) {
        Log("ERROR", tag, msg);
    }
}
