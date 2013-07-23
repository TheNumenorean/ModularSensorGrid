import net.thenumenorean.modularsensorgrid.*
msg = GridCreator.constructNewModularSensorGrid(10);

l = msg.getSensor('LightSensor1');



while 1 == 1
    pause(1);
    fprintf('fin');
    if l.getValue() > 500
        l.setLight(true);
    else l.setLight(false);
    end
end
pop.stop();