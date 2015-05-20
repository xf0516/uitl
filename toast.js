/*!
 * toast.js
 * @author fei.Xia
 * @version 1.0.0
 */
var toast = {
    show: function(message, timeout) {
        var tot = document.getElementById("mytoast");
        if (tot != null) {
            var text = tot.getElementsByTagName("p")[0];
            text.innerText = message;
            return;
        }
        var bg = document.createElement("div");
        var bgstyle = "position: fixed;z-index: 1100;top: 40%;left: 50%;width: 280px;margin-left: -140px;background: rgba(67, 60, 53, 0.6);border-top-left-radius: 8px;border-top-right-radius: 8px;border-bottom-right-radius: 8px;border-bottom-left-radius: 8px;border-radius: 8px;background-clip: padding-box;text-align: center;";
        bg.setAttribute("style", bgstyle);
        bg.setAttribute("id", "mytoast");
        var p = document.createElement("p");
        var pstyle = "font-size:25px; margin:15px 0 11px;line-height: 33px;overflow: hidden;color:white;";
        p.setAttribute("style", pstyle);
        p.innerText = message;
        bg.appendChild(p);
        document.body.appendChild(bg);
        if (timeout != undefined) {
            setTimeout(function() {
                var ta = document.getElementById("mytoast");
                if (ta != null)
                    ta.remove();
            }, timeout);
        }
    },
    hide: function() {
        var ta = document.getElementById("mytoast");
        if (ta != null)
            ta.remove();
    }
}
