package com.example.expansions.construcciones;

import static com.example.expansions.construcciones.ResourceManager.getResourceManager;

class Construction {
    private int to, la, ov, pi, ti;

    Construction(int to, int la, int ov, int pi, int ti) {
        this.to = to;
        this.la = la;
        this.ov = ov;
        this.pi = pi;
        this.ti = ti;
    }

    boolean canBeConstructed() {
        return getResourceManager(ResourceManager.TRONCO).getAmount() >= to
                && getResourceManager(ResourceManager.LADRILLO).getAmount() >= la
                && getResourceManager(ResourceManager.OVEJA).getAmount() >= ov
                && getResourceManager(ResourceManager.TRIGO).getAmount() >= ti
                && getResourceManager(ResourceManager.ROCA).getAmount() >= pi;
    }

    void construct() {
        getResourceManager(ResourceManager.TRONCO).substract(to);
        getResourceManager(ResourceManager.LADRILLO).substract(la);
        getResourceManager(ResourceManager.OVEJA).substract(ov);
        getResourceManager(ResourceManager.ROCA).substract(pi);
        getResourceManager(ResourceManager.TRIGO).substract(ti);
    }

}
