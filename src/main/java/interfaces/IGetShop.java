package interfaces;

import classes.shops.ShowShops;

import java.util.List;

public interface IGetShop {
    List<ShowShops> getShops();
    ShowShops getShopById(String addressId);
    List<ShowShops> getShopsByCorp(String corpId);
    boolean isGammaShop (String addressId);
}
