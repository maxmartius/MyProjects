from pyVoIP.VoIP import VoIPPhone, InvalidStateError, CallState
import time
import wave

def answer(call):
    try:
        f = wave.open('../audio/Natur3.wav', 'rb')
        frames = f.getnframes()
        data = f.readframes(frames)
        f.close()

        call.answer()

        print(2)
        tmp = 1
        sum = "0"
        while call.state == CallState.ANSWERED and tmp < 20:
            dtmf = call.get_dtmf()
            sum += dtmf
            if dtmf == "1":
                print(1)
                call.hangup()
            elif dtmf == "2":
                print(1)
                call.hangup()
            time.sleep(0.1)
            tmp +=1
        print(sum)
    except InvalidStateError:
        pass
    except:
        call.hangup()

if __name__ == "__main__":
    phone = VoIPPhone("sipconnect.sipgate.de", 5060, "1426067t0", "B5krv8tGGMgF", callCallback=answer)
    phone.start()
    input('Press enter to disable the phone')
    phone.stop()
