package com.open.pgp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.SignatureException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.util.io.Streams;

import name.neuhalfen.projects.crypto.bouncycastle.openpgp.BouncyGPG;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.callbacks.KeyringConfigCallbacks;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.keyrings.InMemoryKeyring;
import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.keyrings.KeyringConfigs;

public class Main {

  public static void main(String[] args) {
    // TODO Auto-generated method stub

    String publickey = "mQINBGSe270BEAC8kiNP+tNVLgO0GAc40ShS4uEAUVKcdofk+UJaWruWdRCF7PIY\r\n"
        + "CJfDlJNDES4eFF7Hh2am8M3/rEABRXB+fqKm2QU7oj1byI2ImF+EkHdb9Jxkxir+\r\n"
        + "037IojJXBKHzzbzBLm+hHmYk+L2jtAuyE2WXDecEGxkpCi4s0TQv8RJ9rBtU/ixr\r\n"
        + "KJZI7XCzAjzEfU1dD6x8Au2EjfcdJkz4u4EAOq0W5mkrJvj9b9jnJsNUE0FHclAr\r\n"
        + "Z3pVMcJeG8jYVbZXWNJer39/9wXMpl7oRObJ/rGcGi+AMWcbQXh8R2iPRQap6a/C\r\n"
        + "tFgGs/3bLnLEVyjpTXL0MZ7533S9VqvKJeZ+H1EisMM9c+f5a8OyGLJb3ObaLAf4\r\n"
        + "oxi3ICYD++SxAB28/p15DSsE3J5vku4o2LB7wfcmbH7fteCxEk8aezIOJsb7em2j\r\n"
        + "hNy3zgCx1BCquNYF1zhjpyMGxWwUHIrBKMClxHRo/tE6uC6RRSsAzvImv2FUKnaf\r\n"
        + "B8qb/M2ySZ8ytFnQqa576dD0ffLRO3TnKXD2RgKJjxgIb3zo+WC/az8xSnHihmid\r\n"
        + "TUzEn6FDmSMktVY3iH/JrjwjQOiLR8DxeV5FYdNcrErmp2iGHkWn87Bms4xqxaJF\r\n"
        + "K+d7TGq5p9zPb084TZlRJ+R9VHpUCYkNe2zXQdf0FT3IDWmQBQ1x/rZAEwARAQAB\r\n"
        + "tBdhZG1pbiA8YWRtaW5AZ21haWwuY29tPokCVwQTAQgAQRYhBDt0j9RLC8oPGdn3\r\n"
        + "HRr/oGjPr43oBQJkntu9AhsjBQkDw1MrBQsJCAcCAiICBhUKCQgLAgQWAgMBAh4H\r\n"
        + "AheAAAoJEBr/oGjPr43oqb4QAIPsZjnD7VtoNKuOmBUEhcOik6WZsU5wT5peTwN7\r\n"
        + "OrvLqppA1C4QhA6dLvClCmRCS4yJ093of9zBrgljxr17VE0kQi7gRtVjcxKwFeNi\r\n"
        + "JdgxHtNewpwfAg/gJ6kKEmi+Md6AQcNKuL3jzsAcQ08Mnrk843nlDjZvMRp9U4VA\r\n"
        + "c4k8teBL2JJU9dy4psGXEKkNCQiUFJV+u9mCoHxg1HfreUg6oIdeMnsJBZZ/XdhW\r\n"
        + "9bypdsk9mLSnKFguWU2+uZxZCCtsUI8GP4IW3I3vsuyuI6QNMtEwZ5LjiG2uaECp\r\n"
        + "4wxV9bhjqb0KJ656w67UDYwozK/libpKu9XCaj+A4PCVqUYM0ARjbKyZQrKOWfKq\r\n"
        + "eh4qzx5Jx4uLCBwa02xvoc8EYu89r+hFo3+iMCDB/+WpPg4GfCmwyJu1m6liM0jP\r\n"
        + "TqmwIG1WFzct3SqrACrlX7ANq78ZY3bbjnlFzVGptv3w9N+6v1oFYAAMutry31UP\r\n"
        + "ELF1x+qK0GBVVC0zN94ki1sX+FTiGkHod0rXRrwB+/WLV9gT2L+ry2Xb981RaqHf\r\n"
        + "IKptrHvzYVrOgQ18GGw/0OZkah3l2mJeFHf4yFI9kXzHq4F+FBLVpGElt6VIKFah\r\n"
        + "KklvY8SGxwgpgBsdemRwMXGQOkyK6PLfGM6qbQ7fwM9s9YcCfzB4kFOWi94653wb\r\n"
        + "CGm5uQINBGSe270BEACu5VzZMa6W8/vK3oxJ9Rj0SZvI4lim0XEDLXEw8GdHi+mW\r\n"
        + "QCZppsDx98SAW274oveNnWL9Q3WNdlG8ZZHpzJoYQhVYKvxENazDZ93+RXoGlD+6\r\n"
        + "rWL4ej0/ok06SSw0F6RiJjqDtxCTy6QDpJqTfMSDhDM4M6NyafBfof6z+56uwcD7\r\n"
        + "IvNjha0Gpaw6snLFuUMZbfA3C5BLhgU9DnR2/eDUqU4mEiTRWanf7rYfLiuckrLd\r\n"
        + "Ba6VfHe1WNGorRa8/O2aWEYwsGMHzc8/OYTSgougsCqdubiZOG7LaeOtoxG1gtDM\r\n"
        + "aGBkyCOt7cX37Fc4dLrmeJLcg2LZp6gN5TrQMtZ8JPdTDN9u6EeDzRiVVe8EwmOD\r\n"
        + "yvop5chGXUSKDnkTc9/7UI/60ywXmQOuqt+9JmWxgzyQa1Y3aSkVoWG3dMaUwsgd\r\n"
        + "6/fqoc10KmfbPiU2vM1DpPxkriYP8EwfSua7LzNdHthtbg2Q2TmacI7X6RNSfB9I\r\n"
        + "HAQ2YpF7mFSs+T5sABLH692A2FwUJNTXFPoxyZaepYsUWgCuqzVk/ffEFNQuVUan\r\n"
        + "Emcz88NzzFqrtnivHw1ke8niW4WraP1ZTf73zp/Ni3dP/s6p4mrL5/DB6j6JZ2ao\r\n"
        + "Kdm6ZmD/Mw23K6wx61DJOxpVFlffeB4KvroGhzCeUCZhZ1gWJMs93L7SA1c2mQAR\r\n"
        + "AQABiQI8BBgBCAAmFiEEO3SP1EsLyg8Z2fcdGv+gaM+vjegFAmSe270CGwwFCQPD\r\n"
        + "UysACgkQGv+gaM+vjejVmA//ZduUu/ntlrx1bseJ3e09IV0MT9xEDdfX3QDCWlyh\r\n"
        + "+ZpMuZHsyB/te7hKSFxH3CNnw46yikRLhaauL3AcjkT26X4nBbJrs09DeItlZfLx\r\n"
        + "hyXNly3JSTK2hgNnQnXG/kJ0kpH/18/RsGlvZjXWSHXoJeHKvjqN6DpcLcAIoC7v\r\n"
        + "TiRwkusBlYMygZgkBHnsj0vfUw/3Ukyd0fVBZkq/uVpHswAAZkbxSfcFMmMIcUqN\r\n"
        + "O+7fMs29HavJ2Ds1LFgLn4Mp+X894pPbwyvXMtn23pyPPUrM6xowAY5dJNtOSSeW\r\n"
        + "pHOXzEZiyuzecqVbgYErkeop1LyOl8BbaccXcWebvQCXNz+pB7edKsQuw/N8Jq9S\r\n"
        + "LZ3dscWsQpvTAHB6FZb9/X4MmVPS7xhxQHHKwe9W5xeD7MrHC6Ze8F61KX1oNbjo\r\n"
        + "v0pHBqsqXdx5o7fRzjlHlCTsIKCPO6RGkS3X7BER7gxtEkKoE7jvL5gP4B/NJ2cO\r\n"
        + "VRYOpLJcf5OlxU4PANWV5FTn14+rt6lXMyTT4cp5NcbX0YVo+aANpnoYt/t0EIsK\r\n"
        + "RO7iHQTnRRakylkqKNuB7ScJoWGK8YeUYrBgvN8BNkP6enq2bb1QwHc1X+jaUIcL\r\n"
        + "0VBUJtqCqD+sgNyl+Xr2PMJqUfNXGkvc8WnHsVoF3hoOEg28vhDlHzX6X6pflNg8\r\n" + "jeg=\r\n" + "=3ggX";

    String privatekey = "lQdGBGSe270BEAC8kiNP+tNVLgO0GAc40ShS4uEAUVKcdofk+UJaWruWdRCF7PIY\r\n"
        + "CJfDlJNDES4eFF7Hh2am8M3/rEABRXB+fqKm2QU7oj1byI2ImF+EkHdb9Jxkxir+\r\n"
        + "037IojJXBKHzzbzBLm+hHmYk+L2jtAuyE2WXDecEGxkpCi4s0TQv8RJ9rBtU/ixr\r\n"
        + "KJZI7XCzAjzEfU1dD6x8Au2EjfcdJkz4u4EAOq0W5mkrJvj9b9jnJsNUE0FHclAr\r\n"
        + "Z3pVMcJeG8jYVbZXWNJer39/9wXMpl7oRObJ/rGcGi+AMWcbQXh8R2iPRQap6a/C\r\n"
        + "tFgGs/3bLnLEVyjpTXL0MZ7533S9VqvKJeZ+H1EisMM9c+f5a8OyGLJb3ObaLAf4\r\n"
        + "oxi3ICYD++SxAB28/p15DSsE3J5vku4o2LB7wfcmbH7fteCxEk8aezIOJsb7em2j\r\n"
        + "hNy3zgCx1BCquNYF1zhjpyMGxWwUHIrBKMClxHRo/tE6uC6RRSsAzvImv2FUKnaf\r\n"
        + "B8qb/M2ySZ8ytFnQqa576dD0ffLRO3TnKXD2RgKJjxgIb3zo+WC/az8xSnHihmid\r\n"
        + "TUzEn6FDmSMktVY3iH/JrjwjQOiLR8DxeV5FYdNcrErmp2iGHkWn87Bms4xqxaJF\r\n"
        + "K+d7TGq5p9zPb084TZlRJ+R9VHpUCYkNe2zXQdf0FT3IDWmQBQ1x/rZAEwARAQAB\r\n"
        + "/gcDAtkzlg4hPojI/1M0UlykYutMktLCjwmDHzlJ8bRp4XcCJxVs8bNfx6kK7mAh\r\n"
        + "Vk53bcBECDLu/XttugifKk//SLQf0EC0hyrShzY+IiiO/Bm6P8fAnWeQLkEG0AQk\r\n"
        + "fKJfRJzQ4gOLyhTCvPMfx3GoWsmGZ18anKQKpeBqtyZupKJpiJF4voNDmlqPRaqL\r\n"
        + "/9QizvUVHz7qhLUV1VQGsxXBZi33Rx/ckOjpc8IOSWOXoYVf1BM00d+i5N+Ml5y+\r\n"
        + "3zRqs8AULJOCFt5e3pqKBehwHFZIFd+R94q6ZgU2zTyy8236/bFcK58UdI7XPSDn\r\n"
        + "sa3sidLNQYny5FWtC6BSf5Tvcx+gilLtytlArOsr8MZK3u7rtHes1TVDxr/6C13g\r\n"
        + "a3g896zS++rHrWjmt3IyE5KwbvaUWULLJYgEAtD3LCTHSSOWlC1J0Us+ltAhrnjD\r\n"
        + "YMTJ+WM6kRxbHn8SL9QJ4m4stetrEHMiTbQxpR4Z+6eHyuEf2W7yp6ORhX8cvI30\r\n"
        + "TtwgEx9TvFT+G8gLWKGM08WP0BvB4+9AlzO6Y64EiVP/s27Ch3QGdh+8fd9xnJbx\r\n"
        + "Q2CkBdBWzq4T1VXMEc0bhF2fYxdfepPa/IzIuNfwn8v0RvdkIu3xeEgXmdjR0jXI\r\n"
        + "a7kE0vsJiwi4untb0Lt6tHdPAawRGJh7kvIz7xtZ0Xk9BIdx5wtju3cwjp5FjnL+\r\n"
        + "UTqxlIlU+kcz4WU2ySLFbVE5Rq3msBsMtX3LXgstkVUJxYqKIXFMOuyubDQRigrA\r\n"
        + "6fH+EybtFaBYw4vKvUec+EUWjn6EKbeFiMwgTA68pCMyRvYdFYh73VsQ8pFazW4+\r\n"
        + "BY3OmMB+4erEqjt7xWB6+htDRHwScY1Lik0ze3kT5l8dhYJnYsq9lg2w1yfGng7a\r\n"
        + "kE2RZho8sb9B4yeMnOcxmjtGz5tFQHeK1BVB0vZJqyAhaLS+podFGt3ble9oxCwo\r\n"
        + "zVDFrnTOHHZWuCGIPvIniC/MuQpUFnfRSE0pXW2c1y7Z8kfyLvaj3LB2CRTlNvub\r\n"
        + "f++3tgGMnz2NwVA+ZwInlMO1QWkG3TWZUn1aGKDB4WzbAic7dWbrMyhiXx4QZIsu\r\n"
        + "DPYcMqqTRHrTzAoc0JaCVtm14dXykjN8R28xzXm1QdpNlAQIOdoBIWY3sY1eb8bA\r\n"
        + "XSKxHH76HZO9HQ5KBhi0eUA0pJ04Mxwv6sMtVsT8u55POVaaW29gjrFxizljVu7C\r\n"
        + "VbeMpZWY8g5wuMQ6clqo/wn4BTrsQOCxHSUkLLWtMdEV9xGS63L7FmHm5YeVA/Q+\r\n"
        + "hUEjlB0+os4svubgQgSNFsarHP+Nc9NBnab+aS2Fd2biaHp8RwHbm5fjMIHCYORw\r\n"
        + "Yl551HFV5EGfcbkm4p2ju++kFWfLxXS9IHRK8dKDk0eZeHEKKOfPEHC4fI4+L/vk\r\n"
        + "m/yy56Ng94uXpfaMill8ElWk09HQKx/2b0NKgvlxaqfyBQBLnm0gcREXpW9xocRP\r\n"
        + "Gjq2lVP5L7yHMm/kBOvILflPkD6/4e64OFbKuujZ/0AXyzGbI38hYH+DB3C0jgqw\r\n"
        + "ezOHxHu2xbUShd6bcj36h7ICtB+irITEAhNwXsJby72ls9KRPv7ld+/9IpX82Ac1\r\n"
        + "YM+SvhUssg2BAUYkal6VdUeNVz0c5whNo/9hIniopjeAgjUZ5fVnCVZZcQFbNmkv\r\n"
        + "4jK2sGoorEprk0cvka8AoV4UxmVup2Qj/aKJeVhDPNmAB/zuIh3HmhwylCK4iqM8\r\n"
        + "lx2ztgpeQh6QoW3ihUHHAQ+b9GkTydmVDyCJ1SMvtUN4bummOBTDGFa0F2FkbWlu\r\n"
        + "IDxhZG1pbkBnbWFpbC5jb20+iQJXBBMBCABBFiEEO3SP1EsLyg8Z2fcdGv+gaM+v\r\n"
        + "jegFAmSe270CGyMFCQPDUysFCwkIBwICIgIGFQoJCAsCBBYCAwECHgcCF4AACgkQ\r\n"
        + "Gv+gaM+vjeipvhAAg+xmOcPtW2g0q46YFQSFw6KTpZmxTnBPml5PA3s6u8uqmkDU\r\n"
        + "LhCEDp0u8KUKZEJLjInT3eh/3MGuCWPGvXtUTSRCLuBG1WNzErAV42Il2DEe017C\r\n"
        + "nB8CD+AnqQoSaL4x3oBBw0q4vePOwBxDTwyeuTzjeeUONm8xGn1ThUBziTy14EvY\r\n"
        + "klT13LimwZcQqQ0JCJQUlX672YKgfGDUd+t5SDqgh14yewkFln9d2Fb1vKl2yT2Y\r\n"
        + "tKcoWC5ZTb65nFkIK2xQjwY/ghbcje+y7K4jpA0y0TBnkuOIba5oQKnjDFX1uGOp\r\n"
        + "vQonrnrDrtQNjCjMr+WJukq71cJqP4Dg8JWpRgzQBGNsrJlCso5Z8qp6HirPHknH\r\n"
        + "i4sIHBrTbG+hzwRi7z2v6EWjf6IwIMH/5ak+DgZ8KbDIm7WbqWIzSM9OqbAgbVYX\r\n"
        + "Ny3dKqsAKuVfsA2rvxljdtuOeUXNUam2/fD037q/WgVgAAy62vLfVQ8QsXXH6orQ\r\n"
        + "YFVULTM33iSLWxf4VOIaQeh3StdGvAH79YtX2BPYv6vLZdv3zVFqod8gqm2se/Nh\r\n"
        + "Ws6BDXwYbD/Q5mRqHeXaYl4Ud/jIUj2RfMergX4UEtWkYSW3pUgoVqEqSW9jxIbH\r\n"
        + "CCmAGx16ZHAxcZA6TIro8t8YzqptDt/Az2z1hwJ/MHiQU5aL3jrnfBsIabmdB0YE\r\n"
        + "ZJ7bvQEQAK7lXNkxrpbz+8rejEn1GPRJm8jiWKbRcQMtcTDwZ0eL6ZZAJmmmwPH3\r\n"
        + "xIBbbvii942dYv1DdY12UbxlkenMmhhCFVgq/EQ1rMNn3f5FegaUP7qtYvh6PT+i\r\n"
        + "TTpJLDQXpGImOoO3EJPLpAOkmpN8xIOEMzgzo3Jp8F+h/rP7nq7BwPsi82OFrQal\r\n"
        + "rDqycsW5Qxlt8DcLkEuGBT0OdHb94NSpTiYSJNFZqd/uth8uK5ySst0FrpV8d7VY\r\n"
        + "0aitFrz87ZpYRjCwYwfNzz85hNKCi6CwKp25uJk4bstp462jEbWC0MxoYGTII63t\r\n"
        + "xffsVzh0uuZ4ktyDYtmnqA3lOtAy1nwk91MM327oR4PNGJVV7wTCY4PK+inlyEZd\r\n"
        + "RIoOeRNz3/tQj/rTLBeZA66q370mZbGDPJBrVjdpKRWhYbd0xpTCyB3r9+qhzXQq\r\n"
        + "Z9s+JTa8zUOk/GSuJg/wTB9K5rsvM10e2G1uDZDZOZpwjtfpE1J8H0gcBDZikXuY\r\n"
        + "VKz5PmwAEsfr3YDYXBQk1NcU+jHJlp6lixRaAK6rNWT998QU1C5VRqcSZzPzw3PM\r\n"
        + "Wqu2eK8fDWR7yeJbhato/VlN/vfOn82Ld0/+zqniasvn8MHqPolnZqgp2bpmYP8z\r\n"
        + "DbcrrDHrUMk7GlUWV994Hgq+ugaHMJ5QJmFnWBYkyz3cvtIDVzaZABEBAAH+BwMC\r\n"
        + "EzWBLr+ZQFH/dz5/PAMxu/MjNRXFfhJsf1YZJ/QsoYRIpExZWsuiYv9Ke0yv1ZwZ\r\n"
        + "CZ9CWvMZfyJoRkqxZTuf2a5ZMBvs0GyRKjO/IAMKr/ZWNKQZvuVKyJ16yGUWdTnj\r\n"
        + "uypPQMzF2Blaax3yT7d+TAFrPoRwSCxoPzbonF+B0xUc50EDP4v6EWTmgLeDNR1w\r\n"
        + "oFgfT5zX78jzajnX0IOWkXBMpReiSdDqY8HDVxXyvzdj5I8eTRlaWnGGIJQUln8U\r\n"
        + "qtcxXCude5iXyNU/Nu2v18czR+Fvh/yWtVS2Nzqon5dupol70MU8RVRTMe5IR4+q\r\n"
        + "SDc9M0BtJr+hJiSDRU77OJNedX3IcFNOtTqG7yJg8xpLISsHX5O3BKXNRAfixM3S\r\n"
        + "uJg+YFg4iWWzWUcubvVTYsX6lRmbECWM66rDabQUaO1rfW2F+pixwa3Itrg9sh8Z\r\n"
        + "Dm57GCPFAgYbWMuUxVhpLkSsKUQZnb4TlusbVplgaxWEB1zv9BCchhi7D+wwQr30\r\n"
        + "X6WY0EdzTpB9ouCu28mXoxh7bDuRMhKEcn+o80OH4zOpqLrbpUM+uii9KTTi/gC2\r\n"
        + "XBhokHSGyXtopS7yNO1bc2iyApj1yesX4lGRKjuiTY/rrQMNw/XoP6hKUShDyVuC\r\n"
        + "MouML9JiSLAxP4Bo5EQbqerNNp5xBxU0dPz/MCJdsBKnA2xeuvyc8Omp/u6G0CA5\r\n"
        + "H8p4d2etxNscp0wtKAbxkIyqa8tbp9h1lgeLpQ2zXhD1wrgo5ZU6tJKDcirVp/53\r\n"
        + "lbroutSjpizp7I5pcljQUSRhv7lwqQJIqF9KINt6I/ofv3ZhLF2D/H4P6KtoJ/ue\r\n"
        + "e9CNly5/yh8TB//CQJJZ6lLpCnqSWrfbpw2fpphM0YZlArqYtWkEKqec3DxGtn8z\r\n"
        + "oJXNT54LWcv6LnZtHO2DnRrqZHM8hq68OdxPhANthGWYjNJweG9PLjXb8oHtASI0\r\n"
        + "FqKPoFocT7QfYrNw957d5s3STNpwgd8A/o67wsknaHQHv67fed6ikXVlFnNqTvlB\r\n"
        + "B/wuAvD5J0YMhkfpk9BskxcxL3a7X11OerOvNtblg+iuLGG5lVXE93h5AEZIJHuv\r\n"
        + "qeWMzc7YnVZzNU7L+D3W+VuOPsHmTMjuwiQZEmkA/rrUu7kFZeugNoz8PG7itf6d\r\n"
        + "xW7Zq7NyJvIq6Z5Z3r17LOSrKqJ5ehQR5/k8zOB2gmf8lVEHm1AtYNE5PMCVwTEm\r\n"
        + "fsa/tI54HU9CtpIGR18CTy00W0T0ReYgkDSJmghsUSnySUSrOqlIJ22Z0a5JYZZ0\r\n"
        + "pM+tKRyOS8Na1VnsN9fxB/yVAfk32jb9AGyuYJ7yoVgoD0S+IO2irAwE73u4fgPW\r\n"
        + "Y7W0Cw7Mho3eTHyxpdBr0MLiyED6Z+72CoqkHnfZO3HNJcLyiKhuU3QAUkl5X28s\r\n"
        + "BAoUhzzYZoJ7bxvkAL2HErvJu1+nZwKoWgTXNzTNn2JOj0NzJrW36fY72TTYP97m\r\n"
        + "b7juUaP3JOJXS3Nj0N2zOSQgU/QRJ22UssiwcRjcaFYirigv4fzJhBUA70Omxixd\r\n"
        + "tFM1h2GVRNk/Cj7Dwvo08I2Qrgv2pUZSxGhW7L3oYSbFxMWbR/rGCRiG5PAv1vbW\r\n"
        + "NTyme4RMrdKp7vGHHv3wWs1ceFGUUck+pkUJVeLShJDWx1VhnP7tkh92Tt98gbkr\r\n"
        + "u4PCRL8JGIjE9OJrs9dDct5PiObeYkzgZg3vsbWTZ80iCPE7j1ZDMDYUE10/9YQK\r\n"
        + "0uP8k4g2wuWxEoybq7/k7/dbhGUrtT0gyfiC9wRuVhlbN7E9iokCPAQYAQgAJhYh\r\n"
        + "BDt0j9RLC8oPGdn3HRr/oGjPr43oBQJkntu9AhsMBQkDw1MrAAoJEBr/oGjPr43o\r\n"
        + "1ZgP/2XblLv57Za8dW7Hid3tPSFdDE/cRA3X190AwlpcofmaTLmR7Mgf7Xu4Skhc\r\n"
        + "R9wjZ8OOsopES4Wmri9wHI5E9ul+JwWya7NPQ3iLZWXy8YclzZctyUkytoYDZ0J1\r\n"
        + "xv5CdJKR/9fP0bBpb2Y11kh16CXhyr46jeg6XC3ACKAu704kcJLrAZWDMoGYJAR5\r\n"
        + "7I9L31MP91JMndH1QWZKv7laR7MAAGZG8Un3BTJjCHFKjTvu3zLNvR2rydg7NSxY\r\n"
        + "C5+DKfl/PeKT28Mr1zLZ9t6cjz1KzOsaMAGOXSTbTkknlqRzl8xGYsrs3nKlW4GB\r\n"
        + "K5HqKdS8jpfAW2nHF3Fnm70Alzc/qQe3nSrELsPzfCavUi2d3bHFrEKb0wBwehWW\r\n"
        + "/f1+DJlT0u8YcUBxysHvVucXg+zKxwumXvBetSl9aDW46L9KRwarKl3ceaO30c45\r\n"
        + "R5Qk7CCgjzukRpEt1+wREe4MbRJCqBO47y+YD+AfzSdnDlUWDqSyXH+TpcVODwDV\r\n"
        + "leRU59ePq7epVzMk0+HKeTXG19GFaPmgDaZ6GLf7dBCLCkTu4h0E50UWpMpZKijb\r\n"
        + "ge0nCaFhivGHlGKwYLzfATZD+np6tm29UMB3NV/o2lCHC9FQVCbagqg/rIDcpfl6\r\n"
        + "9jzCalHzVxpL3PFpx7FaBd4aDhINvL4Q5R81+l+qX5TYPI3o\r\n" + "=MFAd";
    try {
      encryptFile(System.getProperty("user.dir") + "/testfiles/sample.xml",
          System.getProperty("user.dir") + "/testfilesencryp/sample.xml", publickey);
      decryptFile(System.getProperty("user.dir") + "/testfilesencryp/sample.xml",
          System.getProperty("user.dir") + "/testfiles/sample1.xml", privatekey, "pgpexample@2023");

      // bytes encryption
      encryptWithBytes("Iam,here".getBytes(), System.getProperty("user.dir") + "/testfilesencryp/sample.txt",
          publickey);
      decryptFile(System.getProperty("user.dir") + "/testfilesencryp/sample.txt",
          System.getProperty("user.dir") + "/testfiles/sample1.txt", privatekey, "pgpexample@2023");

      // byte format encryption and upload as file to FTP server
      encryptAndUploadToFtpWithBytes("Iam,here".getBytes(), "demo.wftpserver.com", 21, "demo", "demo", publickey);

      System.out.print("done");
    } catch (NoSuchProviderException | IOException | PGPException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SignatureException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (NoSuchAlgorithmException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  private static void encryptFile(String inputFilePath, String encryptedFilePath, String pubKey)
      throws IOException, PGPException, SignatureException, NoSuchAlgorithmException, NoSuchProviderException {

    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

    final InMemoryKeyring keyringConfig = KeyringConfigs.forGpgExportedKeys(KeyringConfigCallbacks.withPassword(""));

    keyringConfig.addPublicKey(pubKey.getBytes("US-ASCII"));

    try (final FileOutputStream fileOutput = new FileOutputStream(encryptedFilePath);
        final BufferedOutputStream bufferedOut = new BufferedOutputStream(fileOutput);

        final OutputStream outputStream = BouncyGPG.encryptToStream().withConfig(keyringConfig).withStrongAlgorithms()
            .toRecipient("admin@gmail.com").andDoNotSign().binaryOutput().andWriteTo(bufferedOut); // .andSignWith("Mugunthan@gmail.com").binaryOutput().andWriteTo(bufferedOut);
        final FileInputStream is = new FileInputStream(inputFilePath)) {
      Streams.pipeAll(is, outputStream);
    }
  }

  private static void decryptFile(String encryptedFilePath, String decryptedFilePath, String pvtKey, String password)
      throws IOException, PGPException, NoSuchAlgorithmException, NoSuchProviderException {
    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

    final InMemoryKeyring keyringConfig = KeyringConfigs
        .forGpgExportedKeys(KeyringConfigCallbacks.withPassword(password));

    keyringConfig.addSecretKey(pvtKey.getBytes("US-ASCII"));

    try (final FileInputStream encryptedFileInput = new FileInputStream(encryptedFilePath);
        final BufferedInputStream bufferedIn = new BufferedInputStream(encryptedFileInput);
        final OutputStream decryptedOutput = new FileOutputStream(decryptedFilePath);

        final InputStream inputStream = BouncyGPG.decryptAndVerifyStream().withConfig(keyringConfig)
            .andIgnoreSignatures().fromEncryptedInputStream(bufferedIn)) {
      Streams.pipeAll(inputStream, decryptedOutput);
    }
  }

  private static void encryptWithBytes(byte[] bytes, String encryptedFilePath, String pubKey)
      throws IOException, PGPException, SignatureException, NoSuchAlgorithmException, NoSuchProviderException {

    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

    final InMemoryKeyring keyringConfig = KeyringConfigs.forGpgExportedKeys(KeyringConfigCallbacks.withPassword(""));

    keyringConfig.addPublicKey(pubKey.getBytes("US-ASCII"));

    try (final FileOutputStream fileOutput = new FileOutputStream(encryptedFilePath);
        final BufferedOutputStream bufferedOut = new BufferedOutputStream(fileOutput);

        final OutputStream outputStream = BouncyGPG.encryptToStream().withConfig(keyringConfig).withStrongAlgorithms()
            .toRecipient("admin@gmail.com").andDoNotSign().binaryOutput().andWriteTo(bufferedOut)) {

      outputStream.write(bytes);
      outputStream.flush();
    }
  }

  private static void encryptAndUploadToFtpWithBytes(byte[] bytes, String ftpServer, int ftpPort, String ftpUsername,
      String ftpPassword, String pubKey)
      throws IOException, PGPException, SignatureException, NoSuchAlgorithmException, NoSuchProviderException {

    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

    final InMemoryKeyring keyringConfig = KeyringConfigs.forGpgExportedKeys(KeyringConfigCallbacks.withPassword(""));

    keyringConfig.addPublicKey(pubKey.getBytes("US-ASCII"));

    try (final ByteArrayOutputStream encryptedDataOutput = new ByteArrayOutputStream();
        final OutputStream outputStream = BouncyGPG.encryptToStream().withConfig(keyringConfig).withStrongAlgorithms()
            .toRecipient("admin@gmail.com").andDoNotSign().binaryOutput().andWriteTo(encryptedDataOutput)) {

      outputStream.write(bytes);
      outputStream.flush();

// Connect to FTP server and upload the encrypted data directly
      FTPClient ftpClient = new FTPClient();
      ftpClient.connect(ftpServer, ftpPort);
      ftpClient.login(ftpUsername, ftpPassword);

      if (ftpClient.isConnected()) {
        System.out.println("FTP client is connected.");
      } else {
        System.out.println("FTP client is not connected.");
      }
      // ftpClient.changeWorkingDirectory("/upload");
      InputStream inputStream = new ByteArrayInputStream(encryptedDataOutput.toByteArray());
      ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
      ftpClient.storeFile("sampt.txt", inputStream);

      ftpClient.logout();
      ftpClient.disconnect();
    }
  }

}
