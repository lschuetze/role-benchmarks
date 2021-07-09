import sys
from typing import List

import pandas as pd
import numpy as np
from matplotlib.lines import Line2D
from scipy.stats import gmean
import matplotlib.pyplot as plt
# import seaborn as sns


def load_benchmark_data(path: str) -> List[pd.DataFrame]:
    # Read data
    col_names = ['Invocation', 'Iteration', 'Value', 'Unit', 'Criterion', 'Benchmark',
                 'VM', 'Approach', 'Extra', 'Cores', 'InputSize', 'Var']
    data = pd.read_csv(path, sep='\t', names=col_names, skiprows=4)
    print(f'Run time: {data["Value"].sum() / 1000 / 60} minutes')
    # Drop first iterations
    data = data[~data['Iteration'].isin([1, 2])]
    # Drop unnecessary columns
    col_drop = ['Invocation', 'Iteration', 'Unit', 'Criterion', 'VM', 'Extra', 'Cores', 'InputSize']
    data.drop(col_drop, axis=1, inplace=True)
    # Map innerIterations values
    data['Var'] = data['Var'].map(lambda x: round((x / 1000)**2, 2))
    # Drop problem sizes < 1M
    data = data[data['Var'].isin([1., 1.5, 2., 2.5, 3., 3.5, 4., 4.5, 5., 5.5, 6.])]
    data = data.set_index(['Var', 'Approach']).sort_index()
    # Return separate df for each benchmark
    return [x.drop('Benchmark', axis=1) for _, x in data.groupby('Benchmark')]


def mean_and_errors(data: pd.DataFrame) -> List[pd.DataFrame]:
    grouped = data.groupby(['Var', 'Approach'])
    return grouped.mean(), grouped.std(ddof=0)


def separate_baseline(data: pd.DataFrame, mask: str) -> List[pd.DataFrame]:
    bl = data.xs(mask, level=1, drop_level=False)
    return data.drop(bl.index), bl


def normalize_data(data: pd.DataFrame, baseline: pd.DataFrame) -> pd.DataFrame:
    bl_mean = baseline.groupby(level=0).mean()
    return data / bl_mean


def configure_plt_style():
    plt.style.use('ggplot')
    plt.rcParams['font.family'] = 'Open Sans'
    # sns.set(style='ticks', palette='Set2')


def plot_data(labels: pd.Index, means: list[pd.DataFrame], errors: list[pd.DataFrame],
              appr: pd.Index, norm: bool, title: str, filename: str, geo_mean: np.array = None):
    appr = appr.map({'test-objectteams-classic-38': 'Classic 2020',
                     'test-objectteams-indy-38': 'PDP',
                     'test-objectteams-indy-38-deg': 'PDP+'})
    n = len(appr)
    x = np.arange(len(labels)) * 1.5
    w = round(1.2 / n, 1)
    offsets = (np.arange(n) - 0.5 * (n - 1)) * w
    y = [m.to_numpy().flatten() - int(norm) for m in means]
    yerr = [e.to_numpy().flatten() for e in errors]
    error_kw = {'elinewidth': 0.8, 'capsize': w * 6}
    label_fs, tick_label_fs = 9, 8
    fig, ax = plt.subplots(figsize=(7, 4))

    for i in np.arange(n):
        ax.bar(x=(x + offsets[i]), height=y[i], width=w, bottom=int(norm), yerr=yerr[i], label=appr[i], error_kw=error_kw, log=not norm)
        if norm:
            ax.axhline(geo_mean[i], c=f'C{i}', ls='--', lw=1)

    ylabel = 'Run time factor normalized to Classic 2020 (lower is better)' if norm else 'Run time in ms'
    ax.set_ylabel(ylabel, fontsize=label_fs)
    ax.set_xlabel('Millions of iterations', fontsize=label_fs)
    ax.set_xticks(x)
    ax.set_xticklabels(labels)
    ax.tick_params(labelsize=tick_label_fs)
    handles, labels = ax.get_legend_handles_labels()
    if norm:
        handles.append(Line2D([0], [0], c='0.5', ls='--', lw=1))
        labels.append('Geometric Mean')
    ax.legend(handles=handles, labels=labels, loc=10, bbox_to_anchor=(0., 0.98, 1., .102),
              ncol=len(appr) + int(norm), fontsize='small', frameon=False, borderaxespad=0.)
    ax.set_title(title, {'fontsize': 12}, pad=20)
    fig.tight_layout(pad=0)
    plt.savefig(f'{folder}/{filename}.pdf')
    plt.show()


if len(sys.argv) > 1:
    folder = f'../data/{sys.argv[1]}'
else:
    with open('../data/latest') as f:
        folder = f'../data/{f.read().split("/")[-1]}'

df1, df2 = load_benchmark_data(f'{folder}/benchmark.data')
# ---------------- Plot average run times ----------------
# Calculate mean and std
df1_mean, df1_std = mean_and_errors(df1)
df2_mean, df2_std = mean_and_errors(df2)
# Plot data
configure_plt_style()
iter_vars = df1.index.levels[0]
approaches = df1.index.levels[1]
titles = ['Static Contexts Benchmark', 'Variable Contexts Benchmark']
filenames = ['benchmark_static', 'benchmark_variable',
             'benchmark_static_normalized', 'benchmark_variable_normalized']
plot_data(labels=iter_vars,
          means=[df1_mean.xs(x, level=1) for x in approaches],
          errors=[df1_std.xs(x, level=1) for x in approaches],
          appr=approaches,
          norm=False,
          title=titles[0],
          filename=filenames[0])
plot_data(labels=iter_vars,
          means=[df2_mean.xs(x, level=1) for x in approaches],
          errors=[df2_std.xs(x, level=1) for x in approaches],
          appr=approaches,
          norm=False,
          title=titles[1],
          filename=filenames[1])

# ----------- Plot normalized average runtimes -----------
# Separate baseline
bl_mask = 'test-objectteams-classic-38'
df1, df1_bl = separate_baseline(df1, bl_mask)
df2, df2_bl = separate_baseline(df2, bl_mask)
# Normalize
df1_norm = normalize_data(df1, df1_bl)
df2_norm = normalize_data(df2, df2_bl)
# Calculate mean and std
df1_norm_mean, df1_norm_std = mean_and_errors(df1_norm)
df2_norm_mean, df2_norm_std = mean_and_errors(df2_norm)
approaches = approaches.drop(bl_mask)
# Calculate geometric mean
speedups_static = [gmean(df1_norm_mean.xs(x, level=1))[0] for x in approaches]
speedups_dynamic = [gmean(df2_norm_mean.xs(x, level=1))[0] for x in approaches]
print(f'Geometric mean static case: {speedups_static[0]} (PDP), {speedups_static[1]} (PDP+)')
print(f'Geometric mean variable case: {speedups_dynamic[0]} (PDP), {speedups_dynamic[1]} (PDP+)')
# Plot normalized data
plot_data(labels=iter_vars,
          means=[df1_norm_mean.xs(x, level=1) for x in approaches],
          errors=[df1_norm_std.xs(x, level=1) for x in approaches],
          appr=approaches,
          norm=True,
          title=titles[0],
          filename=filenames[2],
          geo_mean=speedups_static)
plot_data(labels=iter_vars,
          means=[df2_norm_mean.xs(x, level=1) for x in approaches],
          errors=[df2_norm_std.xs(x, level=1) for x in approaches],
          appr=approaches,
          norm=True,
          title=titles[1],
          filename=filenames[3],
          geo_mean=speedups_dynamic)
