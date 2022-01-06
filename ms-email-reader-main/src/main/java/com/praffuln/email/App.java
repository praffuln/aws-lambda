package com.praffuln.email;

import java.time.Instant;
import java.util.Objects;

import com.praffuln.email.authentication.AccessProvider;
import com.praffuln.email.authentication.AuthenticationBuilder;
import com.praffuln.email.handler.FileHandler;
import com.praffuln.email.handler.MessageHandler;
import com.praffuln.email.handler.impl.FileHandlerImpl;
import com.praffuln.email.handler.impl.MessageHandlerImpl;

/**
 * Wrapper class for running the application.
 * 
 * https://github.com/phantom-artist/ms-email-reader
 * 
 */
public class App {

    public static void main(String[] args) {

//        if (args.length < 3) {
//            throw new RuntimeException(
//                "Program requires args <tenantId> <applicationId> <applicationSecret> (optional) <download folder>");
//        }

        // Very simplistic way of passing sensitive info via program params.
        // There are probably other (better) ways of injecting these.
    	//TODO : Put right values
        final String tenantId = "";
        final String applicationId = "";
        final String applicationSecret = "";
         
        Objects.requireNonNull(tenantId, "tenantId cannot be null");
        Objects.requireNonNull(applicationId, "applicationId cannot be null");
        Objects.requireNonNull(applicationSecret, "applicationSecret cannot be null");

        // The directory to download attachments to (defaults to tmp.dir)
        final String attachmentsDownloadPath = 
            null== null ? 
                System.getProperty("java.io.tmpdir") : 
                args[3];
        
        final AccessProvider accessProvider = 
            AuthenticationBuilder.createAccessProvider(
                tenantId, applicationId, applicationSecret);

        final MessageHandler messageHandler = new MessageHandlerImpl(accessProvider);
        final FileHandler fileHandler = new FileHandlerImpl();

        final EmailProcessor emailProcessor = 
            new EmailProcessor(
                attachmentsDownloadPath, 
                messageHandler,
                fileHandler);

        Logger.logInfo("Started processing at " + Instant.now());
        emailProcessor.run();
        Logger.logInfo("Finished processing at " + Instant.now());
        
    }
}
