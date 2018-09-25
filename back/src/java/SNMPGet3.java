
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.ScopedPDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.UserTarget;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.MPv3;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.security.AuthMD5;
import org.snmp4j.security.PrivDES;
import org.snmp4j.security.SecurityLevel;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.TSM;
import org.snmp4j.security.USM;
import org.snmp4j.security.UsmUser;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;


public class SNMPGet3 {
    
    public SNMPGet3() { }
    
    public String SNMPGet3(String strAddress, String community, String strOID, String user, String pass) {
        String str = "";
        
        try {
            TransportMapping transport = new DefaultUdpTransportMapping();
            transport.listen();
            OctetString community1 = new OctetString(community);
            strAddress = strAddress + "/" + 161;
            Address targetaddress = new UdpAddress(strAddress);
            CommunityTarget comtarget = new CommunityTarget();
            OctetString securityName = new OctetString("antonio71");
            
            comtarget.setCommunity(community1);
            //comtarget.setSecurityLevel(SecurityLevel.AUTH_PRIV);
            comtarget.setSecurityLevel(SecurityLevel.NOAUTH_NOPRIV);
            comtarget.setSecurityName(securityName);  
            comtarget.setAddress(targetaddress);
            comtarget.setVersion(SnmpConstants.version3);
            comtarget.setRetries(2);
            comtarget.setTimeout(5000);
            
            OID authProtocol = AuthMD5.ID;
            OID privProtocol = PrivDES.ID;
            OctetString authPassphrase = new OctetString("antonio71pass");
            OctetString privPassphrase = new OctetString("authNoPriv");
            
            PDU pdu = new ScopedPDU();
            pdu.add(new VariableBinding(new OID(strOID)));
            pdu.setType(PDU.GET);
            ResponseEvent event;
                    
            Snmp snmp = new Snmp(transport);
            
            /*OctetString localEngineId = new OctetString(MPv3.createLocalEngineID());
            USM usm = new USM(SecurityProtocols.getInstance(), localEngineId, 0);
            SecurityModels.getInstance().addSecurityModel(usm);*/
            
            UsmUser user2 = new UsmUser(securityName, authProtocol, authPassphrase, privProtocol, privPassphrase);
            snmp.getUSM().addUser(securityName, user2);
            //SecurityModels.getInstance().addSecurityModel(new TSM(localEngineId, false));
            
            
            event = snmp.get(pdu, comtarget);

            if (event != null) {
                pdu = event.getResponse();
                System.out.println(pdu);
                if (pdu.getErrorStatus() == PDU.noError) {
                  System.out.println("SNMPv3 SET Successful!");
                  PDU pduresponse = event.getResponse();
                    str = pduresponse.getVariableBindings().firstElement().toString();
                    if (str.contains("=")) {
                        int len = str.indexOf("=");
                        str = str.substring(len + 1, str.length());
                    }
                } else {
                  System.out.println("SNMPv3 SET Unsuccessful.");
                }
            } else {
              System.out.println("SNMP send unsuccessful.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if(str.length() > 0){
            return str;
        }
        
        return "No se encontro";
    }
}
