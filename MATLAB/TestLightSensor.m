import net.thenumenorean.modularsensorgrid.*
import net.thenumenorean.modularsensorgrid.datacapture.*
import net.thenumenorean.modularsensorgrid.usb.ardulink.*

msg = ModularSensorGrid();
ArdulinkGridPopulator.populate(msg);

l = msg.getSensor('LightSensor1');

msg.addDataCaptureTool(GraphicalCaptureTool());
msg.start();

while 1 == 1
    pause(.01);
    if l.getLightIntensity() > 700
        l.setStatusLight(true);
    else l.setStatusLight(false);
    end
end

msg.destroy();