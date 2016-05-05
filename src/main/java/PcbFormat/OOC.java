package PcbFormat;

import java.util.List;
import java.util.Stack;
import java.util.ArrayList;
import static PcbFormat.CommandUtil.escape;
import static PcbFormat.CommandUtil.colorBlackTech;

/**
 * Created by pca006132 on 2016/5/5.
 */
public class OOC {
    Stack<SingleOOC> oocs = new Stack<>();
    public OOC(String[] commands) throws PcbParseException{
        oocs.push(new SingleOOC());
        for (String cmd : commands) {
            if (oocs.peek().canAddCommand(cmd))
                oocs.peek().addCommand(cmd);
            else {
                oocs.push(new SingleOOC());
                oocs.peek().addCommand(cmd);
            }
        }
    }

    public String[] getOOCs() {
        List<String> results = new ArrayList<>();
        for (SingleOOC ooc : oocs) {
            results.add(ooc.getOOC());
        }
        return results.toArray(new String[results.size()]);
    }
}
