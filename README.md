# Webhooks Module

> :warning: This is a **DiSky** module, not a **Bukkit Plugin!**

## Installation

1. Download DiSky and place it in the `/plugins/` directory.
2. Download this (through releases) and place it in the `/plugins/DiSkymodules/` directory.
3. Restart the server.

## Examples

```python
on script load:
    # Register the webhook. To create one, go in a channel's settings, then Integrations, then Webhooks. Click on 'Copy URL' and paste it in the console.
    register webhook named "example" with url "https://discord.com/api/webhooks/XXX/XXXX"
   
command webhooks:
    trigger:
        # Even if a webhook have a pre-defined avatar and name, we can change that at every message.
        make a new webhook message:
 
            set webhook name of builder to "Test" # The new name. It DOES NOT affect the defined name.
            # Here's the avatar and the text content, both optional.
            set webhook avatar of builder to "https://www.pngall.com/wp-content/uploads/4/Coronavirus-PNG-Image.png"
            set webhook content of builder to "This is a test message"
            
            # Let's make an embed using DiSky's syntax:
            make embed:
                set title of embed to "Hey"
                set embed color of embed to orange
               
            # Then we just set the current embeds to the embed we just made.
            set webhook embeds of builder to last embed
            
            # Finally, we can make the client send the message we just made.
            # Because of Discord's limitations, we can only get back the message's ID.
            make client "example" send builder and store it in {_msgId}
            
            # OPTIONAL: Use the 'retrieve message' effect from DiSky in order to get an actual Message.
            send "Message id: %{_msgId}%" to console
```

### Results:

![Results](https://media.discordapp.net/attachments/491994251341856779/989619417409282148/unknown.png)
