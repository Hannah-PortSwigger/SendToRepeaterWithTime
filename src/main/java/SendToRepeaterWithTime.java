import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.http.message.HttpRequestResponse;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SendToRepeaterWithTime implements BurpExtension
{
    @Override
    public void initialize(MontoyaApi api)
    {
        api.extension().setName("Send to Repeater with time");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMddyyyy-HH:mm:ss");

        api.userInterface().registerContextMenuItemsProvider(event ->
        {
            JMenuItem menuItem = new JMenuItem("Send to Repeater with time");
            menuItem.addActionListener(l -> {
                List<HttpRequestResponse> requestResponseList = event.messageEditorRequestResponse().isPresent() ? List.of(event.messageEditorRequestResponse().get().requestResponse()) : event.selectedRequestResponses();

                for (HttpRequestResponse requestResponse : requestResponseList)
                {
                    api.repeater().sendToRepeater(requestResponse.request(), dateTimeFormatter.format(LocalDateTime.now()));
                }
            });

            return List.of(menuItem);
        });
    }
}
