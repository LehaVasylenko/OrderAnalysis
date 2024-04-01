package interfaces;

import classes.adapter.PrepsInShop;

import java.util.List;

public interface IPrepShop {
    List<PrepsInShop> getApiAvailable();
    PrepsInShop getPrepByShopAndId(String idShop, String idDrug);
}
