package interfaces;

import sftpreader.shopPrice.PriceItem;
import sftpreader.shopPrice.ShopPrice;

import java.util.List;

public interface ISftpManager {
    List<ShopPrice> getShopPrice();
    PriceItem getItemByShopAndId(String idShop, String idDrug);
}
