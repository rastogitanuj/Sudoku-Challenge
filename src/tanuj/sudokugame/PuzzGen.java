package tanuj.sudokugame;

import java.util.Random;

public class PuzzGen {
	
	Random randomGenerator = new Random(System.currentTimeMillis());
	private int puzzleNum;
	private final int num_puzz = 14;
	
	public String generatePuzz(int diff){
		puzzleNum = randomGenerator.nextInt(num_puzz);
		if(diff==0)
			return Epuzzles[puzzleNum];
		else if(diff==1)
			return Mpuzzles[puzzleNum];
		else
			return Hpuzzles[puzzleNum];
	}
	
	private String[] Epuzzles= {
			"030050000800076210090100000" +
			"000020706000000050063000090" +
			"901000007020800000050040600",
			"360000000004230800000004200" +
			"070460003820000014500013020" +
			"001900000007048300000000045",
			"009405080217030000000102009" +
			"605000704090000010108000503" +
			"400901000000020948080704100",
			"000000007204600053080325000" +
			"020030090000400006007006000" +
			"503000000000000920100870600",
			"301080000508604000040010000" +
			"010090080902000406050020010" +
			"000060020000809103000070609",
			"000900000900043100800300002" +
			"050080307080106050304050080" +
			"040001005000670004008003000",
			"900806000060090500000105030" +
			"807500203010000050305001708" +
			"030407000008050040000608007",
			"003021004500080000000406800" +
			"040060210100070005008000003" +
			"000050070095000040700000000",
			"360000000004230800000004200" +
			"070460003820000014500013020" +
			"001900000007048300000000045",
			"003021004500080000000406800" +
			"040060210100070005008000003" +
			"000050070095000040700000000",
			"009405080217030000000102009" +
			"605000704090000010108000503" +
			"400901000000020948080704100",
			"000900000900043100800300002" +
			"050080307080106050304050080" +
			"040001005000670004008003000",
			"900806000060090500000105030" +
			"807500203010000050305001708" +
			"030407000008050040000608007",
			"030050000800076210090100000" +
			"000020706000000050063000090" +
			"901000007020800000050040600",
			"030050000800076210090100000" +
			"000020706000000050063000090" +
			"901000007020800000050040600",
			"679004020000200040100800005" +
			"800000430000000000021000009" +
			"000001004450008007007500302",
				
	};
	private String[] Mpuzzles= {
			
			"301080000508604000040010000" +
			"010090080902000406050020010" +
			"000060020000809103000070609",
			"270006000504002370000750000" +
			"809000605426000738000000000" +
			"000270060052004007000008041",
			"000000007204600053080325000" +
			"020030090000400006007006000" +
			"503000000000000920100870600",
			"000900000900043100800300002" +
			"050080307080106050304050080" +
			"040001005000670004008003000",
			"900806000060090500000105030" +
			"807500203010000050305001708" +
			"030407000008050040000608007",
			"003021004500080000000406800" +
			"040060210100070005008000003" +
			"000050070095000040700000000",
			"030050000800076210090100000" +
			"000020706000000050063000090" +
			"901000007020800000050040600",
			"270006000504002370000750000" +
			"809000605426000738000000000" +
			"000270060052004007000008041",
			"003021004500080000000406800" +
			"040060210100070005008000003" +
			"000050070095000040700000000",
			"360000000004230800000004200" +
			"070460003820000014500013020" +
			"001900000007048300000000045",
			"003021004500080000000406800" +
			"040060210100070005008000003" +
			"000050070095000040700000000",
			"009405080217030000000102009" +
			"605000704090000010108000503" +
			"400901000000020948080704100",
			"679004020000200040100800005" +
			"800000430000000000021000009" +
			"000001004450008007007500302",
			"030050000800076210090100000" +
			"000020706000000050063000090" +
			"901000007020800000050040600"
				
	};
	private String[] Hpuzzles= {
			"301080000508604000040010000" +
			"010090080902000406050020010" +
			"000060020000809103000070609",
			"003021004500080000000406800" +
			"040060210100070005008000003" +
			"000050070095000040700000000",
			"270006000504002370000750000" +
			"809000605426000738000000000" +
			"000270060052004007000008041",
			"000000007204600053080325000" +
			"020030090000400006007006000" +
			"503000000000000920100870600",
			"000900000900043100800300002" +
			"050080307080106050304050080" +
			"040001005000670004008003000",
			"900806000060090500000105030" +
			"807500203010000050305001708" +
			"030407000008050040000608007",
			"270006000504002370000750000" +
			"809000605426000738000000000" +
			"000270060052004007000008041",
			"360000000004230800000004200" +
			"070460003820000014500013020" +
			"001900000007048300000000045",
			"009405080217030000000102009" +
			"605000704090000010108000503" +
			"400901000000020948080704100",
			"000000007204600053080325000" +
			"020030090000400006007006000" +
			"503000000000000920100870600",
			"000900000900043100800300002" +
			"050080307080106050304050080" +
			"040001005000670004008003000",
			"900806000060090500000105030" +
			"807500203010000050305001708" +
			"030407000008050040000608007",
			"030050000800076210090100000" +
			"000020706000000050063000090" +
			"901000007020800000050040600",
			"030050000800076210090100000" +
			"000020706000000050063000090" +
			"901000007020800000050040600",
			"679004020000200040100800005" +
			"800000430000000000021000009" +
			"000001004450008007007500302"
	};
	
}
