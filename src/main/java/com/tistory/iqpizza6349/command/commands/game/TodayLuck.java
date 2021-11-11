package com.tistory.iqpizza6349.command.commands.game;

import com.tistory.iqpizza6349.command.CommandContext;
import com.tistory.iqpizza6349.command.ICommand;
import com.tistory.iqpizza6349.database.MySQLDatabase;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.*;

public class TodayLuck implements ICommand {

    HashMap<Integer, String> lucky = new HashMap<>();

    public DecimalFormat decimalFormat = new DecimalFormat("00");
    public static Calendar calendar = Calendar.getInstance(Locale.KOREA);

    public TodayLuck() {
        lucky.put(1, "The Fool");
        lucky.put(2, "The Magician");
        lucky.put(3, "The High Priestess");
        lucky.put(4, "The Empress");
        lucky.put(5, "The Emperor");
        lucky.put(6, "The Hierophant");
        lucky.put(7, "The Lovers");
        lucky.put(8, "The Chariot");
        lucky.put(9, "Strength");
        lucky.put(10, "The Hermit");
        lucky.put(11, "Wheel of Fortune");
        lucky.put(12, "Justice");
        lucky.put(13, "The Hanged Man");
        lucky.put(14, "Death");
        lucky.put(15, "Temperance");
        lucky.put(16, "The Devil");
        lucky.put(17, "The Tower");
        lucky.put(18, "The Star");
        lucky.put(19, "The Moon");
        lucky.put(20, "The Sun");
        lucky.put(21, "Judgement");
        lucky.put(22, "The World");
    }

    @Override
    public void handle(CommandContext ctx) {
        calendar.add(Calendar.DATE, 0);
        calendar.add(Calendar.MONTH, 0);
        calendar.add(Calendar.YEAR, 0);

        User user = ctx.getMessage().getAuthor();
        TextChannel channel = ctx.getChannel();

        Random rand = new Random();

        String s = getCard(user.getIdLong());
        String check = null;
        if(s != null){
            if(!s.equals("null")){
                check = s.split("-")[0];
            }
        }

        String to = decimalFormat.format(calendar.get(Calendar.DAY_OF_MONTH));

        String today = to + decimalFormat.format(calendar.get(Calendar.MONTH)) +
                decimalFormat.format(calendar.get(Calendar.YEAR));
        boolean lastDate = getDate(user.getIdLong());

        if(lastDate){
            resetToday(user.getIdLong());
            check = null;
        }

        String card = lucky.get(rand.nextInt(22));
        if(check != null){
            card = check;
        }

        if(card == null){
            card = lucky.get(rand.nextInt(22));
        }

        if (card.equals(lucky.get(1))) {
            channel.sendMessage(user.getName() + "님의 오늘의 운세는 " + card + " 입니다.\n").queue();
            channel.sendMessage("카드 뜻 : 이 카드는 모험, 출발이라는 뜻도 있지만, 경솔하고 어리석다는 뜻도 있어요.").queue();
            channel.sendMessage("\n운세 풀이 : 오늘은 무언가 시작이 될 것 같군요 하지만 조금 불안정한 시작이에요." +
                    "\n예를 들어 학교에서 급히 새로운 과제를 내 줄 수도 있을것 같아요. 또 대학 입학일 수도 있고요. 그리고 오늘은 행동과 말을 조금 더 생각해본 뒤 실행해보는 건 어떨까요?").queue();
            updateToday(card, today,user.getIdLong());
        }
        else if (card.equals(lucky.get(2))) {
            channel.sendMessage(user.getName() + "님의 오늘의 운세는 " + card + " 입니다.\n").queue();
            channel.sendMessage("카드 뜻 : 이 카드는 재능, 창조라는 뜻이 있어요, 하지만 겁 많음, 기만이라는 뜻도 있죠.").queue();
            channel.sendMessage("\n운세 풀이 : 오늘 시작하는 일이 아주 잘될 거에요, 또 새로운 계획이 성공할 것이고요. 오늘은 상상력과 자신의 아이디어를 살리는 게 좋아 보여요." +
                    "\n하지만 오늘 사기를 당할 수도 있으니 조심하셔야 해요.").queue();
            updateToday(card, today,user.getIdLong());
        }
        else if (card.equals(lucky.get(3))) {
            channel.sendMessage(user.getName() + "님의 오늘의 운세는 " + card + " 입니다.\n").queue();
            channel.sendMessage("\n카드 뜻 : 지식, 지혜를 뜻합니다. 반대로 무지, 무례함을 뜻하기도 하죠").queue();
            channel.sendMessage("\n운세 풀이 : 오늘은 당신의 지식이 빛을 발할 것 같아요. 또 침착함을 잃지 않을 것 같아요." +
                    "\n하지만 조금 외로워 보이기도 해요. 오늘 자기 생각 또는 고민에 너무 집착하지는 마세요. 그게 힘들다면 잠시 바람을 쐬는 건 어떨까요?").queue();
            updateToday(card, today,user.getIdLong());
        }
        else if (card.equals(lucky.get(4))) {
            channel.sendMessage(user.getName() + "님의 오늘의 운세는 " + card + " 입니다.\n").queue();
            channel.sendMessage("카드 뜻 :풍요 모성을 뜻하고, 동요 태만 과잉을 뜻합니다. ").queue();
            channel.sendMessage("\n운세 풀이 : 오늘은 여성스러움이 강한 것 같아요. 혹은 예술적 재능, 포용력이 폭발하는 날이에요. 주목을 잘 받을 수도 있겠어요." +
                    "\n오늘은 동요하지 말고 태만하지 않으면 좋을 것 같아요.").queue();
            updateToday(card, today,user.getIdLong());
        }
        else if (card.equals(lucky.get(5))) {
            channel.sendMessage(user.getName() + "님의 오늘의 운세는 " + card + " 입니다.\n").queue();
            channel.sendMessage("카드 뜻 : 지배 책임 부성을 뜻하고, 오만 미숙을 뜻합니다.").queue();
            channel.sendMessage("\n운세 풀이 : 오늘은 의지력이 빛을 발해 오늘 할 일을 잘 끝마칠 것 같아요. 오늘은 체력이 충분해 쌩쌩 할 수도 있고요, 남이 당신에게 더욱 기댈 수 있을 것 같아요." +
                    "\n하지만 업무 과다나 실무 능력이 부족하면 무리해서 할 필요가 없어요. 허세를 부리지 않는 게 좋아요.").queue();
            updateToday(card, today,user.getIdLong());
        }
        else if (card.equals(lucky.get(6))) {
            channel.sendMessage(user.getName() + "님의 오늘의 운세는 " + card + " 입니다.\n").queue();
            channel.sendMessage("카드 뜻 : 가르침 관대함을 뜻하고, 나태 신용이 떨어짐을 의미하기도 해요").queue();
            channel.sendMessage("\n운세 풀이 : 오늘은 솔직한 조언을 받을 것 같아요. 또한 당신이 다른 사람을 이끌어 갈 수 있을 거예요." +
                    "\n하지만 너무 조언에만 메어있으면 좋지 않을 수 있어요. 또 누군가에게 신용을 잃을 수 있고요.").queue();
            updateToday(card, today,user.getIdLong());
        }
        else if (card.equals(lucky.get(7))) {
            channel.sendMessage(user.getName() + "님의 오늘의 운세는 " + card + " 입니다.\n").queue();
            channel.sendMessage("카드 뜻 : 연애 결합이라는 뜻이 있고, 배신, 실연이라는 뜻이 있어요.").queue();
            channel.sendMessage("\n운세 풀이 : 오늘은 사랑이 이루어질 수 있을 것 같아요. 또한 인연이 찾아올 수도 있고요. 협력자나 파트너가 좋은 사람일 경우가 많아요." +
                    "\n하지만 갑자기 실연을 맞을 수 있고, 혼란스러운 일이 있을 수 있어요. 그리고 배신을 당할 수도 있으니 조심하는 게 좋아요.").queue();
            updateToday(card, today,user.getIdLong());
        }
        else if (card.equals(lucky.get(8))) {
            channel.sendMessage(user.getName() + "님의 오늘의 운세는 " + card + " 입니다.\n").queue();
            channel.sendMessage("카드 뜻 : 전진 승리라는 뜻이 있고, 좌절 패배라는 뜻이 있어요.").queue();
            channel.sendMessage("\n운세 풀이 : 오늘은 뭔가 이길 일이 있을 것 같네요. 오늘은 하나의 전차가 된 거 같이 적극적으로 행동하는 것이 좋을 것 같아요." +
                    "\n하지만 너무 성급하면 오히려 독이 될 수 있으니 조심하는 게 좋아요.").queue();
            updateToday(card, today,user.getIdLong());
        }
        else if (card.equals(lucky.get(9))) {
            channel.sendMessage(user.getName() + "님의 오늘의 운세는 " + card + " 입니다.\n").queue();
            channel.sendMessage("카드 뜻 : 의지 용기의 뜻이 있고, 무기력 자만이라는 뜻이 있어요").queue();
            channel.sendMessage("\n운세 풀이 : 오늘은 이제까지 해결하지 못한 난관을 의지력으로 해결할 수 있을 것 같아요." +
                    "\n하지만 오늘 무기력하거나 인내심이 부족하다면 쉬는 것도 하나의 방법이에요.").queue();
            updateToday(card, today,user.getIdLong());
        }
        else if (card.equals(lucky.get(10))) {
            channel.sendMessage(user.getName() + "님의 오늘의 운세는 " + card + " 입니다.\n").queue();
            channel.sendMessage("카드 뜻 : 탐구, 사려 깊은이라는 뜻이 있고, 폐쇄적 음습이라는 뜻이 있어요").queue();
            channel.sendMessage("\n운세 풀이 : 오늘 하는 모든 일에 신중함이 묻어 나오는 것 같아요. 조용히 묵묵히 할 일을 하는 것 같기도 하고요." +
                    "\n하지만 쓸쓸해 보이기도 해요. 오늘은 비밀을 누설하거나 침묵을 깨는 일이 없도록 조심하는 게 좋아요.").queue();
            updateToday(card, today,user.getIdLong());
        }
        else if (card.equals(lucky.get(11))) {
            channel.sendMessage(user.getName() + "님의 오늘의 운세는 " + card + " 입니다.\n").queue();
            channel.sendMessage("카드 뜻 : 윤회 일시적인 행운이라는 뜻이 있고, 오산 불운의 뜻이 있어요.").queue();
            channel.sendMessage("\n운세 풀이 : 오늘은 전환기를 맞을 것 같아요. 생각지 못한 행운이 찾아올 수 도 있고요." +
                    "\n하지만 오늘은 예상에 빗겨나가는 일이 있을 수 있어요. 불리한 입장이 될 수도 있으니 조심하는 게 좋아요.").queue();
            updateToday(card, today,user.getIdLong());
        }
        else if (card.equals(lucky.get(12))) {
            channel.sendMessage(user.getName() + "님의 오늘의 운세는 " + card + " 입니다.\n").queue();
            channel.sendMessage("카드 뜻 : 균형 정당함을 뜻하고, 편견 부정이라는 뜻이 있어요.\n").queue();
            channel.sendMessage("운세 풀이 : 오늘은 당신이 조정하는 역할을 할 것 같아요. 중립적인 입장을 지키는 것이 좋아 보여요." +
                    "\n분쟁이나 소송이 일어날 수 있으니 대처할 준비를 해놓아도 좋을 것 같아요. 또 오늘은 선입견을 버리는 것이 어떨까요?").queue();
            updateToday(card, today,user.getIdLong());
        }
        else if (card.equals(lucky.get(13))) {
            channel.sendMessage(user.getName() + "님의 오늘의 운세는 " + card + " 입니다.\n").queue();
            channel.sendMessage("카드 뜻 : 희생 인내라는 뜻이 있고, 무의미한 희생과 맹목의 뜻이 있어요.").queue();
            channel.sendMessage("\n운세 풀이 : 오늘은 명예로운 희생을 할 수 있을 것 같은 날이에요. 무슨 일이든 수행이라고 생각하면 좋을 거예요." +
                    "\n하지만 굳이 사서 고생할 필요는 없어요. 오늘은 무슨 일이든 포기하지 말아보는 게 좋아요.").queue();
            updateToday(card, today,user.getIdLong());
        }
        else if (card.equals(lucky.get(14))) {
            channel.sendMessage(user.getName() + "님의 오늘의 운세는 " + card + " 입니다.\n").queue();
            channel.sendMessage("카드 뜻 : 격변 끝이라는 뜻이 있고, 변화에 저항 앞으로 나아갈 수 없음이라는 뜻도 있어요.").queue();
            channel.sendMessage("\n운세 풀이 : 오늘은 무언가 크게 변화할 것 같아요. 변화를 위해 이때까지 이루어온 것을 포기해야 할 수도 있어요." +
                    "\n하지만 손해를 보더라도 나아가기 위해 버리는 것이 나아 보여요. 용기 있게 변화에 직면하는 것이 좋아 보여요." +
                    "\n카드 뜻 그대로 누군가가 죽을 수도 있으니 마음을 단단히 먹는 것이 좋아요.").queue();
            updateToday(card, today,user.getIdLong());
        }
        else if (card.equals(lucky.get(15))) {
            channel.sendMessage(user.getName() + "님의 오늘의 운세는 " + card + " 입니다.\n").queue();
            channel.sendMessage("카드 뜻 : 조화와 스스로 자제라는 뜻이 있고, 낭비 극단적인 이란 뜻이 있어요.").queue();
            channel.sendMessage("운세 풀이 : 오늘은 당신의 생각을 한 번 더 생각해 보고 말하는 것이 좋을 것 같아요. 열린 자세로 상대방을 받아들일 준비를 해 두는 것도 좋을 것 같아요." +
                    "\n오늘은 당신을 절제하며 욕망을 조절하는 것이 중요해요.").queue();
            updateToday(card, today,user.getIdLong());
        }
        else if (card.equals(lucky.get(16))) {
            channel.sendMessage(user.getName() + "님의 오늘의 운세는 " + card + " 입니다.\n").queue();
            channel.sendMessage("카드 뜻 : 사심 의존이라는 뜻이 있고, 안 좋은 상황으로부터 회복 떨쳐내고 자유로워짐이라는 뜻이 있어요.").queue();
            channel.sendMessage("\n운세 풀이 : 오늘은 당신이 만들어낸 상황에 억압되어 있을 수도 있어요. 과하게 약속이 잡혀 있다면 조금은 취소하는 것이 좋아요." +
                    "\n또 당신이 두려움과 부정적인 생각이 있다면 그것을 직면해 포용하는 것이 중요해요. 힘들겠지만 포용한 뒤에는 한껏 자유로워질 거예요.").queue();
            updateToday(card, today,user.getIdLong());
        }
        else if (card.equals(lucky.get(17))) {
            channel.sendMessage(user.getName() + "님의 오늘의 운세는 " + card + " 입니다.\n").queue();
            channel.sendMessage("카드 뜻 : 재난 파멸을 뜻하고, 변화에 대한 두려움 재앙을 회피라는 뜻이 있어요.").queue();
            channel.sendMessage("\n운세 풀이 : 오늘은 무언가 극적으로 바뀔 것 같아요. 바뀔 것을 지키는 것보다 빠르게 포기하고 새롭게 무언가를 다시 구축하는 게 좋아요." +
                    "\n갑자기 무언가 변하고, 잃고, 무너지기 때문에 마음을 단단히 먹는 게 좋아요.").queue();
            updateToday(card, today,user.getIdLong());
        }
        else if (card.equals(lucky.get(18))) {
            channel.sendMessage(user.getName() + "님의 오늘의 운세는 " + card + " 입니다.\n").queue();
            channel.sendMessage("카드 뜻 : 희망 평화라는 뜻이 있고, 절망 환멸이라는 뜻이 있어요.").queue();
            channel.sendMessage("\n운세 풀이 : 오늘은 희망찰 거예요. 당신의 판단을 믿는 것이 좋아요. 오늘은 당신이 이루고 싶었던 것을 이룰 수 있을 거예요." +
                    "\n하지만 자신감이 부족하거나 비관적인 태도를 취하면 당신이 즐기는 것을 방해할 수 있어요. 되도록 부정적인 태도보다 긍정적인 태도를 취해보는 것은 어떨까요?").queue();
            updateToday(card, today,user.getIdLong());
        }
        else if (card.equals(lucky.get(19))) {
            channel.sendMessage(user.getName() + "님의 오늘의 운세는 " + card + " 입니다.\n").queue();
            channel.sendMessage("카드 뜻 : 불안 자기 기만이란 뜻이 있고, 혼돈의 끝 모든 것을 해결 이란 뜻이 있어요.").queue();
            channel.sendMessage("\n운세 풀이 : 오늘은 뭔가 불안한 감정과 혼란을 가지고 있을 것 같아요. 이러한 감정을 가지게 하는 것에 대해 알아내는 것이 중요해요. 또한 말하지 못한 것과 비밀스러운 것들이 밝혀질 것이에요." +
                    "\n이 혼란이 끝날 때까지 결론을 내지 않는 것이 좋아요. 오늘은 당신의 직관을 신뢰해 보는 것도 좋아요.").queue();
            updateToday(card, today,user.getIdLong());
        }
        else if (card.equals(lucky.get(20))) {
            channel.sendMessage(user.getName() + "님의 오늘의 운세는 " + card + " 입니다.\n").queue();
            channel.sendMessage("카드 뜻 : 밝은 미래 성공이란 뜻과, 일시적인 우울감 실패라는 뜻이 있어요.").queue();
            channel.sendMessage("\n운세 풀이 : 오늘 하는 일이 다 성공할 것 같아요. 또 활력이 넘치고 건강할 거예요." +
                    "\n하지만 당신이 가진 목표가 비현실적인 것이라면 성공하지 못할 수도 있어요. 또 원하는 것을 얻어도 결과가 마음에 안들 수도 있고요." +
                    "\n자신의 성공에 오만하게 대하지 않는 것이 중요해요.").queue();
            updateToday(card, today,user.getIdLong());
        }
        else if (card.equals(lucky.get(21))) {
            channel.sendMessage(user.getName() + "님의 오늘의 운세는 " + card + " 입니다.\n").queue();
            channel.sendMessage("카드 뜻 : 심판 부활이란 뜻과, 후회 확신이 없는 이란 뜻이 있어요.").queue();
            channel.sendMessage("\n운세 풀이 : 오늘은 새로운 발전에 대해 준비를 해야 할 것 같아요. 당신의 인생계획을 바꿀 중요한 결정을 만날 수도 있고요. 변화를 선택할 때 잠재력을 알아차린다면 그 결과는 유익할 거예요." +
                    "\n또 당신에게 모든 일이 유리하게 적용될 것 같아요." +
                    "\n하지만 잘못된 선택을 하지 않는 것이 중요해요. 중요한 결정을 피하지 않고, 진실을 마주하는 것이 힘들지라도 마주하고 과거를 버리는 것이 좋아요.").queue();
            updateToday(card, today,user.getIdLong());
        }
        else if (card.equals(lucky.get(22))) {
            channel.sendMessage(user.getName() + "님의 오늘의 운세는 " + card + " 입니다.\n").queue();
            channel.sendMessage("카드 뜻 : 완성 완전한이라는 뜻과, 미완성 아직 끝나지 않은이라는 뜻이 있어요.").queue();
            channel.sendMessage("\n운세 풀이 : 오늘은 당신이 한 노력에 마지막에 달했어요. 일이 순조롭게 진행되고 있네요. 이 일을 끝내고 새로운 것을 시작하려고 할 것 같아요. 또는 멀리 여행을 가는 것도 나쁘지 않아 보여요." +
                    "\n하지만 일이 끝나지 않았다면 지금 무엇이 필요한지 모르기 때문일 가능성이 커요. 목표를 달성하기 위해 잠시 멈추고 전체적인 상황을 살펴보는 것이 어떨까요?").queue();
            updateToday(card, today,user.getIdLong());
        }
    }

    private String getCard(long userId){
        try(final PreparedStatement preparedStatement = MySQLDatabase
                .getConnection()
                .prepareStatement("SELECT today FROM user_info WHERE user_id = ?")){
            preparedStatement.setString(1, String.valueOf(userId));

            try (final  ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next())
                    return resultSet.getString("today");
            }

            try (final PreparedStatement statement = MySQLDatabase
                    .getConnection()
                    .prepareStatement("INSERT INTO user_info(user_id) VALUES(?)")) {
                statement.setString(1, String.valueOf(userId));

                statement.executeUpdate();
            }

        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    private void updateToday(String card, String date, long userId) {
        try (final PreparedStatement preparedStatement = MySQLDatabase
                .getConnection()
                .prepareStatement("UPDATE user_info SET today = ? WHERE user_id = ?")) {
            preparedStatement.setString(1, card + "-" + date);
            preparedStatement.setString(2, String.valueOf(userId));

            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private void resetToday(long userId) {
        try (final PreparedStatement preparedStatement = MySQLDatabase
                .getConnection()
                .prepareStatement("UPDATE user_info SET today = ? WHERE user_id = ?")) {
            preparedStatement.setNull(1, Types.NULL);
            preparedStatement.setString(2, String.valueOf(userId));

            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private boolean getDate(long userId){
        try (final PreparedStatement preparedStatement = MySQLDatabase
                .getConnection()
                .prepareStatement("SELECT today FROM user_info WHERE user_id = ?")) {
            preparedStatement.setString(1, String.valueOf(userId));

            String date = null;
            String t = decimalFormat.format(calendar.get(Calendar.DAY_OF_MONTH));

            String today = t + decimalFormat.format(calendar.get(Calendar.MONTH)) +
                    decimalFormat.format(calendar.get(Calendar.YEAR));
            
            try (final  ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()) {
                    String d = resultSet.getString("today");
                    if (d != null) {
                        date = d.split("-")[1];
                    }
                }
            }

            if (date != null) {
                if(!today.equals(date))
                    return true;
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public String getName() {
        return "운세";
    }

    @Override
    public String getHelp() {
        return "타로 카드를 사용하여 오늘의 운세를 볼 수 있습니다.";
    }
}
