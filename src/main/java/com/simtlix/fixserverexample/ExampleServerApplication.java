package com.simtlix.fixserverexample;

import quickfix.*;
import quickfix.field.*;
import quickfix.fix50.ExecutionReport;
import quickfix.fix50.MarketDataRequest;
import quickfix.fix50.MarketDataSnapshotFullRefresh;
import quickfix.fix50.NewOrderSingle;
import quickfix.fixt11.Logon;

/**
 * By https://blog.10pines.com/es/2011/11/21/protocolo-fix-aspectos-basicos-y-utilizacion-en-java-mediante-la-librera-quickfixj/
 */
public class ExampleServerApplication extends ApplicationAdapter {

    @Override
    public  void fromAdmin(Message message, SessionID sessionId) throws FieldNotFound, RejectLogon {
        if (message instanceof Logon) {
            if (!usuarioYPasswordCorrectos((Logon) message)) {
                throw new RejectLogon();
            }
        }
    }

    private  boolean usuarioYPasswordCorrectos(Logon logon) throws FieldNotFound {
        return logon.getUsername().getValue().equals("usuario")
                && logon.getPassword().getValue().equals("password");
    }

    @Override
    public  void fromApp(Message message, SessionID sessionId) throws FieldNotFound, IncorrectTagValue,
            UnsupportedMessageType {
        if (message instanceof MarketDataRequest) {
            MarketDataRequest marketDataRequest = ((MarketDataRequest) message);
            MarketDataSnapshotFullRefresh marketDataSnapshotFullRefresh = new MarketDataSnapshotFullRefresh();
            String mDReqID = marketDataRequest.getMDReqID().getValue();
            marketDataSnapshotFullRefresh.set(new MDReqID(mDReqID));
            StringField stringField58 = new StringField(58, "response");
            marketDataSnapshotFullRefresh.setField(stringField58);
            try {
                Session.sendToTarget(marketDataSnapshotFullRefresh, sessionId);
            } catch (SessionNotFound e) {
                throw new RuntimeException(e);
            }
        }
    }

}
